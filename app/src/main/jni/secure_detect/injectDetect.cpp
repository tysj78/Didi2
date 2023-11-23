#include <string>
#include <set>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <sstream>
#include <iostream>
#include <errno.h>
#include <string.h>
#include <vector>
#include <map>
#include <queue>
#include <jni.h>
#include <android/log.h>

#include "elfParser.h"
#include "memmap.h"

using namespace std;

map<string,char> g_whiteModule;//含有全路径
set<string> g_user_whiteModule;//用户自定义名单，只含有名称，无全路径
string global_so_path;


bool StartWith(const string& content, const string& prefix)
{
	int i=0;
	int size = prefix.size();
	for(; i<size; ++i)	
	{
		if(content[i] != prefix[i])
			break;
	}

	if(i==size)
		return true;
	return false;
}

bool DetectModule(map<string, char> app_map, map<string, char> white_map, vector<string>& result)
{
    //log_info("detect module ...");
	bool l_ret = false;
	map<string, char>::iterator iterator_app_map = app_map.begin();
	while(iterator_app_map != app_map.end())
	{
		if(white_map.find(iterator_app_map->first) == white_map.end())
		{   //not find, find sys lib
            // /system/lib/*
            // /system/bin/*
            // /system/vendor/lib/*
            // /data/dalvik-cache/arm/*
            // /system/app/*
            // [vectors]
//			if(StartWith(iterator_app_map->first, "/system/lib/") ||
//               StartWith(iterator_app_map->first, "/system/bin/") ||
//               StartWith(iterator_app_map->first, "/system/vendor/lib/") ||
//               StartWith(iterator_app_map->first, "/data/dalvik-cache/arm/") ||
//               StartWith(iterator_app_map->first, "/system/app/") ||
//               iterator_app_map->first == "[vectors]"
//			)
//			{
//				//cout<<"insert white list:"<<iterator_app_map->first<<endl;
//                white_map.insert(make_pair(iterator_app_map->first, 1));
//			}
//			else
//			{
//				result.push_back(iterator_app_map->first);
//                l_ret = true;
//			}

			result.push_back(iterator_app_map->first);
			white_map.insert(make_pair(iterator_app_map->first, 1));
			l_ret = true;
		}
		else
		{
		}
		++iterator_app_map;
	}

	return l_ret;
}

vector<string> Split(const string& s, char delimiter)
{
   vector<string> tokens;
   string token;
   istringstream tokenStream(s);
   while (getline(tokenStream, token, delimiter))
   {
        if(token.size() != 0)
            tokens.push_back(token);
   }
   return tokens;
}

void SplitMapString(string& msg, map<string, char>& app_map)
{
	vector<string> lines = Split(msg, '\n');
	vector<string>::iterator iterator_lines = lines.begin();
	int i=0;
	while(iterator_lines != lines.end())
	{
		vector<string> words = Split(*iterator_lines, ' ');
		if(words.size() == 6)
		{
		    if(words[1][2] == 'x')//have exec permission
            {
                app_map.insert(make_pair(words[5], 'x'));
                if(words[5].find("libcheckstatus.so") != string::npos)
                    global_so_path = words[5];
            }
		}

		++i;
		++iterator_lines;
	}
}


int DetectMap(vector<string>& result)
{
	string map_path = "/proc/";
	pid_t pid = getpid();
	std::ostringstream oss;
	oss << pid;
    map_path += oss.str();
    map_path += "/maps";

    //log_info("map path:%s", map_path.c_str());

	int fd = open(map_path.c_str(), O_RDONLY);
	if(fd == -1)
	{
		cout<<"open map err:"<< strerror(errno)<<endl;
        __android_log_print(ANDROID_LOG_DEBUG, "detect", "open map err:%s", strerror(errno));
		return -1;
	}

	string content;
	char buf[1025];
	ssize_t size = 0;
	while((size = read(fd, buf, 1024)) != -1)
	{
		if(size <= 0)
			break;
		buf[size] = 0;
		content += buf;
	}

    map<string, char> l_app_map;//path,exe
	SplitMapString(content, l_app_map);


	map<string, char>::iterator iterator_app_map = l_app_map.begin();
	while(iterator_app_map != l_app_map.end())
	{
		if(iterator_app_map->second == 'x')
		    cout<<iterator_app_map->first<<" "<<iterator_app_map->second<<endl;
		++iterator_app_map;
	}

	if(DetectModule(l_app_map, g_whiteModule, result)) {
        cout << "find threats!" << endl;
    }

	close(fd);
    return 0;
}

int InitMapsWhiteModule()
{
	string map_path = "/proc/";
	pid_t pid = getpid();
	std::ostringstream oss;
	oss << pid;
	map_path += oss.str();
	map_path += "/maps";

	//log_info("map path:%s", map_path.c_str());

	int fd = open(map_path.c_str(), O_RDONLY);
	if(fd == -1)
	{
		cout<<"open map err:"<< strerror(errno)<<endl;
        __android_log_print(ANDROID_LOG_DEBUG, "detect", "open map err:%s", strerror(errno));
		return -1;
	}

	string content;
	char buf[1025];
	ssize_t size = 0;
	while((size = read(fd, buf, 1024)) != -1)
	{
		if(size <= 0)
			break;
		buf[size] = 0;
		content += buf;
	}

	SplitMapString(content, g_whiteModule);

	close(fd);
	return 0;
}

bool IsInUserWhiteList(string path)
{
	int p = path.rfind('/');
	string moduleName = path.c_str() + p + 1;

	if(g_user_whiteModule.find(moduleName) == g_user_whiteModule.end())
		return false;
	return true;
}

vector<string> getAllDependencies(string modulename)
{
    vector<string> result;
    queue<string> con;
    char install_prefix[512];


    vector<string> prefix;
    snprintf(install_prefix, 512, "%s", global_so_path.c_str());
    int a = global_so_path.find("libcheckstatus.so");
    install_prefix[a] = 0;
    prefix.push_back(install_prefix);
	//32system
    prefix.push_back("/system/lib/");
    prefix.push_back("/system/vendor/lib/");
	prefix.push_back("/system/lib/hw/");
	prefix.push_back("/system/lib/egl/");
	prefix.push_back("/system/lib/drm/");
	//64system
	prefix.push_back("/system/lib64/");
	prefix.push_back("/system/lib64/drm/");
	prefix.push_back("/system/lib64/hw/");
	prefix.push_back("/system/lib64/mediadrm/");
	prefix.push_back("/system/lib64/soundfx/");
	prefix.push_back("/vendor/lib/");
	prefix.push_back("/vendor/lib/egl/");
	prefix.push_back("/vendor/lib/hw/");
	prefix.push_back("/vendor/lib/hwcam/");
	prefix.push_back("/vendor/lib/mediadrm/");
	prefix.push_back("/vendor/lib/soundfx/");
	prefix.push_back("/vendor/lib64/");
	prefix.push_back("/vendor/lib64/egl/");
	prefix.push_back("/vendor/lib64/hw/");
	prefix.push_back("/vendor/lib64/hwcam/");
	prefix.push_back("/vendor/lib64/soundfx/");



    con.push(modulename);

    while(con.size() != 0)
    {
        string cur = con.front();
        con.pop();
        result.push_back(cur);

        vector<string> deps = getDependence(cur, prefix);
        if(deps.size() != 0)
        {
            vector<string>::iterator dep = deps.begin();
            while(dep != deps.end())
            {
                con.push(*dep);
                ++dep;
            }
        }
    }

    return result;
}

void setOnePassModule(string module)
{
    vector<string> deps = getAllDependencies(module);

    vector<string>::iterator one = deps.begin();
    while(one != deps.end())
    {
        g_user_whiteModule.insert(*one);
        ++one;
    }
}

extern "C"
{
    JNIEXPORT jstring JNICALL
    Java_com_msmsdk_checkstatus_utiles_ModuleDetect_findThreadModule(JNIEnv * env, jobject thiz)
    {
        //log_info("entry Java_com_msm_hook_secure_ModuleDetect_findThreadModule");

        vector<string> result;
        string str_ret;
        DetectMap(result);
        if(result.size() > 0)
        {
            //发现可疑模块，更新module白名单
            vector<string> policy;
            //g_pm.GetRoster(54, policy);//F_WHITE_SO

//			jobject m_object = (*env).NewGlobalRef(thiz);//创建对象的本地变量
//			jclass clazz = (*env).GetObjectClass(thiz);//获取该对象的类
//			jmethodID m_mid =(*env).GetMethodID(clazz, "notifyFiledChange", "()V");//获取JAVA方法的ID
//			jfieldID m_fid = (*env).GetFieldID(clazz,"a","I");//获取java变量的ID

//			(*env).CallVoidMethod(m_object,m_mid);

            if(policy.size() != 0)
            {
                vector<string>::iterator one = policy.begin();
                while(one != policy.end())
                {
                    setOnePassModule(*one);
                    ++one;
                }
            }

            vector<string>::iterator iterator_threat = result.begin();
            while(iterator_threat != result.end())
            {
				if(IsInUserWhiteList(*iterator_threat))
					;//g_whiteModule.insert(make_pair(*iterator_threat, 'x'));
				else {
					str_ret += *iterator_threat;
					str_ret += " ";
				}

//                str_ret += *iterator_threat;
//                str_ret += " ";

                g_whiteModule.insert(make_pair(*iterator_threat, 'x'));
                ++iterator_threat;
            }
        }

        //log_info("threat modules %s", str_ret.c_str());

        jstring jresult = env->NewStringUTF("");;
        if(str_ret.size() != 0)
            jresult = env->NewStringUTF(str_ret.c_str());
        return jresult;
    }

	JNIEXPORT void JNICALL
	Java_com_msmsdk_checkstatus_utiles_ModuleDetect_updateMoudle(JNIEnv * env , jobject obj, jstring msg)
	{
		jboolean isCopy = false;
		const char* c_str = env->GetStringUTFChars(msg, &isCopy);
		//decrypt data

		//
		vector<string> module_list = Split(c_str, '\n');
		vector<string>::iterator iterator_module = module_list.begin();
		while(iterator_module != module_list.end())
		{
			g_whiteModule.insert(make_pair(*iterator_module, 1));
			++iterator_module;
		}
	}

	void deleteWhiteModule(string name)
	{
		map<string,char>::iterator iter = g_whiteModule.begin();
		for(;iter!= g_whiteModule.end(); iter++)
		{
			string module = iter->first;
			if(module.find(name) != module.npos)
			{
				g_whiteModule.erase(module);
				break;
			}
		}
	}

	JNIEXPORT void JNICALL
	Java_com_msmsdk_checkstatus_utiles_ModuleDetect_InitMoudle(JNIEnv * env)
	{
		InitMapsWhiteModule();
		//删除内置frida-agent, substrate, xposed, XposedBridge.jar
        deleteWhiteModule("frida-agent");
        deleteWhiteModule("libsubstrate.so");
        deleteWhiteModule("libdexposed.so");
        deleteWhiteModule("XposedBridge.jar");
	}

	JNIEXPORT void JNICALL
	Java_com_msmsdk_checkstatus_utiles_ModuleDetect_setOnePassModule(JNIEnv * env, jobject obj, jstring msg)
	{
		char path[1024];

		jboolean isCopy = false;
		const char* c_str = env->GetStringUTFChars(msg, &isCopy);

        setOnePassModule(c_str);
	}

	JNIEXPORT void JNICALL
	Java_com_msmsdk_checkstatus_utiles_ModuleDetect_resetStatus(JNIEnv * env)
	{
		g_whiteModule.clear();
	}
}
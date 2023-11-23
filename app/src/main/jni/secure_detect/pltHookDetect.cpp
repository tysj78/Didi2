//
// Created by uniking on 18-1-31.
//


#include <jni.h>
#include <dlfcn.h>

#include <map>
#include <string>
#include <fcntl.h>
#include <bits/ioctl.h>

using namespace std;

typedef void* (*dlopen_fun)(const char *filename, int flags);
typedef int (*open_fun)(const char *pathname, int flags, mode_t mode);
typedef int (*ioctl_fun)(int __fd, int __request, ...);

map<string, char*> g_real_addr;

void InitRealAddr()
{
    dlopen_fun my_dlopen = (dlopen_fun)dlopen;
    open_fun my_open = (open_fun)open;
    ioctl_fun my_ioctl = (ioctl_fun)ioctl;

    g_real_addr.insert(make_pair("dlopen",(char*)my_dlopen));
    g_real_addr.insert(make_pair("open",(char*)my_open));
    g_real_addr.insert(make_pair("ioctl",(char*)my_ioctl));
}

extern "C"
{
    JNIEXPORT jstring JNICALL
    Java_com_msm_hook_secure_PltHookDetect_Detect(JNIEnv * env)
    {
        string str_ret = "";
        dlopen_fun my_dlopen = (dlopen_fun)dlopen;
        open_fun my_open = (open_fun)open;
        ioctl_fun my_ioctl = (ioctl_fun)ioctl;

        char buf[128];
        if(g_real_addr.find("dlopen")->second != (char*)my_dlopen)
        {
            snprintf(buf, 128, "dlopen is hooked, realaddr=%p newaddr=%p\n", g_real_addr.find("dlopen")->second, my_dlopen);
            str_ret += buf;
        }

        if(g_real_addr.find("open")->second != (char*)my_open)
        {
            snprintf(buf, 128, "open is hooked, realaddr=%p newaddr=%p\n", g_real_addr.find("open")->second, my_open);
            str_ret += buf;
        }

        if(g_real_addr.find("ioctl")->second != (char*)my_ioctl)
        {
            snprintf(buf, 128, "ioctl is hooked, realaddr=%p newaddr=%p\n", g_real_addr.find("ioctl")->second, my_ioctl);
            str_ret += buf;
        }

        jstring jresult = env->NewStringUTF("");
        if(str_ret.size() != 0)
            jresult = env->NewStringUTF(str_ret.c_str());
        return jresult;
    }
}
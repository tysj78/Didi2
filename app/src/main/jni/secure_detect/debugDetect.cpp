//
// Created by famgy on 1/23/18.
//

#include <jni.h>
#include <cstdlib>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <android/log.h>
#include <string>

#define BUFF_LEN 2048

int tracer_pid(char *path)
{
    char buf[BUFF_LEN];
    int trace_pid = 0;

    FILE *fp = fopen(path, "r");
    if (NULL == fp) {
        return 0;
    }

    while (NULL != fgets(buf, BUFF_LEN, fp))
    {
        if (strstr(buf, "TracerPid")) {
            char *strok_rPtr;
            char *temp;

            temp = strtok_r(buf, ":", &strok_rPtr);
            temp = strtok_r(NULL, ":", &strok_rPtr);
            trace_pid = atoi(temp);

            break;
        }
    }

    fclose(fp);

    return trace_pid;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_msmsdk_checkstatus_utiles_DebugDetect_findTracerPid(JNIEnv *env, jobject /* this */) {
    int pid = getpid();
    char path[128] = {0};

    snprintf(path, 128, "/proc/%d/status", pid);
    int iPid = tracer_pid(path);

    return (jint)iPid;
}

size_t strlcpy(char *destStr, const char *srcStr, size_t size)
{
    size_t ret = strlen(srcStr);
    if (size)
    {
        size_t len = (ret >= size)?size-1:ret;
        memcpy(destStr, srcStr, len);
        destStr[len] = '\0';
    }

    return ret;
}

static int tracer_name(const int iPid, char *pcTraceName, int iLen)
{
    char cmd[125] = {0};
    snprintf(cmd, 125, "top -n 1 | grep %d", iPid);

    FILE *fd = popen(cmd, "r");
    if (NULL == fd) {
        __android_log_print(ANDROID_LOG_DEBUG, "detect", "popen failed\n");
        return -1;
    }

    std::string msg;
    char buf[1024];
    while(fgets(buf, 1024, fd) != NULL)
    {
        //log_info("get process : %s", buf);
        msg += buf;
    }

    pclose(fd);

    //log_info("msg : %s", msg.c_str());

    int i;
    for(i = msg.size() - 1; i >= 0; i--)
    {
        if (msg[i] == ' ') {
            break;
        }
    }

    strlcpy(pcTraceName, msg.c_str() + i + 1, iLen);

    __android_log_print(ANDROID_LOG_DEBUG, "detect", "pcTraceName : %s\n", pcTraceName);

    return 0;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_msmsdk_checkstatus_utiles_DebugDetect_findTracerName(JNIEnv *env, jobject /* this */, jint pid) {
    char szTraceName[2048] = {0};

    tracer_name(pid, szTraceName, sizeof(szTraceName));


    std::string trace_name = szTraceName;

    if (szTraceName[0] != '\0')
    {
        __android_log_print(ANDROID_LOG_DEBUG, "detect", "debug_name is %s\n", szTraceName);
    } else {
        __android_log_print(ANDROID_LOG_DEBUG, "detect", "debug_name is none\n");
    }

    return env->NewStringUTF(trace_name.c_str());
}

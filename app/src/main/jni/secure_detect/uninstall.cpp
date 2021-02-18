//
// Created by wzl on 3/18/19.
//

#include <jni.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/inotify.h>
#include <cstdio>
#include <cstring>
#include <errno.h>
#include <poll.h>
#include <android/log.h>
#include "uninstall.h"

void inotify_handle(int fd, char* url)
{
    __android_log_print(ANDROID_LOG_DEBUG, "msm", "uninstall start %s\n", url);
}

void inotify_main(char *path,char *url)
{
    struct pollfd poll_list[2];
    poll_list[0].fd = inotify_init();
    poll_list[0].events = POLLIN;
    int wd = inotify_add_watch(poll_list[0].fd, path, IN_DELETE | IN_CREATE);
    if(wd < 0) {
        fprintf(stderr, "could not add watch for %s, %s\n", path, strerror(errno));
        return ;
    }
    int retval;
    while(1)
    {
        retval = poll(poll_list,(unsigned long)1,-1);

        __android_log_print(ANDROID_LOG_DEBUG, "msm", "uninstall start %s\n", url);

        /* retval 总是大于0或为-1，因为我们在阻塞中工作 */
        if(retval < 0)
        {
            __android_log_print(ANDROID_LOG_DEBUG, "msm", "poll error %s\n", strerror(errno));
            return;
        }
        if((poll_list[0].revents & POLLIN) == POLLIN)
        {
            inotify_handle(poll_list[0].fd,url);
        }

        __android_log_print(ANDROID_LOG_DEBUG, "msm", "inotify exit\n");
    }
    inotify_rm_watch(poll_list[0].fd,wd);
}

extern "C"
JNIEXPORT jint JNICALL Java_com_msmsdk_monitor_Reguninstall_monitor(JNIEnv* env, jobject thiz, jstring path,
                                                            jstring url)
{
    char *listenpath = (char*) env->GetStringUTFChars(path, 0);
    char *jumpurl = (char*) env->GetStringUTFChars(url, 0);
    pid_t pid;
    pid = fork();
    if(pid == 0)
    {
        //子进程
        __android_log_print(ANDROID_LOG_DEBUG, "msm", "entry son process");
        inotify_main(listenpath,jumpurl);
    }
    //父进程不阻塞调用 waitpid  ok 子进程变成了孤儿进程，被init进程收养了
    pid = waitpid(-1,0,1);
    return 0;
}
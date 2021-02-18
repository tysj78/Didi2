//
// Created by wzl on 3/25/20.
//

#ifndef MSMSDK_SYSTEM_H
#define MSMSDK_SYSTEM_H

#include <time.h>
#include <sys/syscall.h>
#include <unistd.h>
#include <jni.h>

typedef time_t (*sys_time)(time_t* __t);
sys_time systime = time;
static int(*old_kill)(pid_t pid, int sig) = kill;

extern "C"
JNIEXPORT jlong JNICALL
Java_com_msmsdk_checkstatus_utiles_SystemTools_getSystemTime(JNIEnv *env, jobject obj){
    //通过系统调用获取时间
    //long tid = syscall(SYS_time);



    //检测time,syscall有没有被hook
    return systime(NULL);
}

extern "C"
JNIEXPORT void JNICALL
Java_com_msmsdk_checkstatus_utiles_SystemTools_kill(JNIEnv *env, jobject obj){
    old_kill(0, 9);
}


#endif //MSMSDK_SYSTEM_H

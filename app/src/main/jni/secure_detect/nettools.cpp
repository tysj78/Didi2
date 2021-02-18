//
// Created by wzl on 9/12/19.
//

#include "nettools.h"

extern "C"
{
JNIEXPORT jboolean JNICALL
Java_com_msmsdk_checkstatus_utiles_SystemTools_isPortUsing(JNIEnv * env, jobject thiz, jint port) {
        struct sockaddr_in sa;
        memset(&sa, 0, sizeof(sa));
        sa.sin_family = AF_INET;
        sa.sin_port = htons(port);
        inet_aton("127.0.0.1", &(sa.sin_addr));
        int sock = socket(AF_INET, SOCK_STREAM, 0);
        if (connect(sock, (struct sockaddr *) &sa, sizeof sa) != -1) {
            return true;
        }

        return false;
    }
}
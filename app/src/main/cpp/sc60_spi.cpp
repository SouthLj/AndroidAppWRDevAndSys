//
// Created by 系统二部刘见 on 2019/10/9.
//

#include <cstring>
#include <fcntl.h>
#include "jni.h"

/**
 * @author liujian
 * @date 2019-07-02
 * @brief open uart
 * @param deviceName:uart index
 * @return file descriptor
 */
static int spiOpen(char const* deviceName){
    int fd=open(deviceName,O_RDWR);//读写方式 int fd=open(deviceName,O_RDWR|O_NONBLOCK);
    if(fd<0){
        return -1;
    }

    return fd;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_sc60devicesutils_Sc60Spi_open_1native(JNIEnv *env, jobject instance,
                                                       jstring spiName_) {
    const char *spiName = env->GetStringUTFChars(spiName_, 0);

    char item_value[128];
    strcpy(item_value, spiName);
    env->ReleaseStringUTFChars(spiName_, spiName);

    return spiOpen(item_value);
}
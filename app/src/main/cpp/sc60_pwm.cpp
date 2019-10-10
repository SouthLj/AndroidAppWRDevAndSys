//
// Created by liujian on 2019/10/10.
//

#include <cstring>
#include <fcntl.h>
#include "jni.h"
//#include "../../../../../../android/Sdk/android-ndk-r20-windows-x86_64/android-ndk-r20/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/fcntl.h"

/**
 * @author liujian
 * @date 2019-07-02
 * @brief open
 * @param deviceName:
 * @return file descriptor
 */
static int pwmOpen(char const* deviceName){
    int fd=open(deviceName,O_RDWR);//读写方式 int fd=open(deviceName,O_RDWR|O_NONBLOCK);
    if(fd<0){
        return -1;
    }

    return fd;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_sc60devicesutils_Sc60Pwm_open_1native(JNIEnv *env, jobject instance,
                                                       jstring pwmName_) {
    const char *pwmName = env->GetStringUTFChars(pwmName_, 0);

    // TODO
    char item_value[128];
    strcpy(item_value, pwmName);
    env->ReleaseStringUTFChars(pwmName_, pwmName);

    return pwmOpen(item_value);
}
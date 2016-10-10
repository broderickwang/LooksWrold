//
//NDK下的C/C++函数和Java桥接的函数命名是有约束的，规则如下：
//                      Java_PackageName_ClassName_MethodName
// Created by Broderick on 2016/10/10.
//
#include <jni.h>
#include "string"

extern "C"
jstring
Java_marc_com_lookswrold_activity_SplashActivity_getStringJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


#include <jni.h>
#include <stdio.h>
#include "org_jiakesimk_minipika_framework_util_Objects.h"

using namespace std;

/*
 * Class:     org_jiakesimk_minipika_framework_util_Objects
 * Method:    sizeof
 * Signature: (Ljava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_org_jiakesimk_minipika_framework_util_Objects_sizeof
  (JNIEnv *env, jclass cls, jobject o)
  {
      printf("Hello World!\n");
      jlong size = sizeof(o);
      return size;
  }
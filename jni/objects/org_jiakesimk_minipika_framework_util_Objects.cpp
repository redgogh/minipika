#include 'org_jiakesimk_minipika_framework_util_Objects.h'

JNIEXPORT jlong JNICALL Java_org_jiakesimk_minipika_framework_util_Objects_sizeof
  (JNIEnv *,
  jclass,
  jobject) {
    return sizeof(jobject)
  }
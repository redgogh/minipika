#include "org_jiakesimk_minipika_framework_util_Objects.h"

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_jiakesimk_minipika_framework_util_Objects
 * Method:    sizeof
 * Signature: (Ljava/lang/Object;)J
 */
JNIEXPORT jlong JNICALL Java_org_jiakesimk_minipika_framework_util_Objects_sizeof
  (JNIEnv *env, jclass cls, jobject o)
  {
      jlong size = sizeof(o);
      return size;
  }

#ifdef __cplusplus
}
#endif
package org.jiakesimk.minipika.framework.utils

import javassist.ClassPool
import javassist.CtClass
import org.jiakesimk.minipika.framework.common.ConstVariable

class JavassistUtils {

  static ClassPool pool = ConstVariable.CLASS_POOL

  static CtClass[] toCtClass(Class<?>... clazz) {
    def ctClass = []
    clazz.each {
      ctClass.add pool.get(it.name)
    } as CtClass[]
  }

}

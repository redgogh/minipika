package groovy

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.Modifier
import javassist.bytecode.CodeAttribute
import javassist.bytecode.LocalVariableAttribute
import javassist.bytecode.MethodInfo
import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.mql.groovy.MqlProxy
import org.junit.Test

import java.lang.reflect.Method

class MqlMapper {

  @Select("""
    select * from user_info where 1=1
    #if isNotEmpty(user.name)
      and name = #user.name
    #end
    #if user.name != 0 && user.name != null
      and age > #user.age
    #end
    and money >= #user.money
  """)
  def findUser(User user, String wdnmd) {}

  @Test
  void test() {
    new MqlProxy(MqlMapper.class)
  }

}
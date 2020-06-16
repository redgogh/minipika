package groovy

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.Modifier
import javassist.bytecode.CodeAttribute
import javassist.bytecode.LocalVariableAttribute
import javassist.bytecode.MethodInfo
import org.jiakesimk.minipika.components.annotation.Select
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
    ClassPool pool = ClassPool.getDefault()
    CtClass ctClass = pool.get('groovy.MqlMapper')
    CtClass ctUser = pool.get('groovy.User')
    CtClass ctString = pool.get("java.lang.String")
    CtMethod ctMethod = ctClass.getDeclaredMethod('findUser', ctUser, ctString)
    MethodInfo methodInfo = ctMethod.getMethodInfo()
    CodeAttribute codeAttribute = methodInfo.codeAttribute
    String[] paramNames = new String[ctMethod.getParameterTypes().length]
    LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag)
    if (attr != null)  {
      int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
      for (int i = 0; i < paramNames.length; i++){
        paramNames[i] = attr.variableName(i + pos);
      }
      println paramNames
    }
  }

}
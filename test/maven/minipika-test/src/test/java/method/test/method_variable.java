package method.test;

/*
 * Creates on 2019/11/13.
 */

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.jiakesimk.minipika.framework.thread.Threads;

/**
 * @author lts
 */
public class method_variable {

  /**
   * @param args
   */
  public static void main(String[] args) {
    String[] names = getMethodVariableName("com.yanek.soa.test.Test", "test2");
    for (String name : names) {
      System.out.println(name);
    }
  }


  /**
   * 获取方法的参数变量名称
   *
   * @param classname
   * @param methodname
   * @return
   */
  public static String[] getMethodVariableName(String classname, String methodname) {
    try {
      ClassPool pool = ClassPool.getDefault();
      pool.insertClassPath(new ClassClassPath(Threads.getCaller()));
      CtClass cc = pool.get(classname);
      CtMethod cm = cc.getDeclaredMethod(methodname);
      MethodInfo methodInfo = cm.getMethodInfo();
      CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
      String[] paramNames = new String[cm.getParameterTypes().length];
      LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
      if (attr != null) {
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++) {
          paramNames[i] = attr.variableName(i + pos);
        }
        return paramNames;
      }
    } catch (Exception e) {
      System.out.println("getMethodVariableName fail " + e);
    }
    return null;
  }

}

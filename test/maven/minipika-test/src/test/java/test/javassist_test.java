package test;

/*
 * Creates on 2019/11/13.
 */

import com.alibaba.fastjson.JSONObject;
import com.minipika.mapper.user_mapper;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.junit.Test;

/**
 * @author lts
 * @email jiakesiws@gmail.com
 */
public class javassist_test {

  @Test
  public void test() throws NotFoundException {
    System.out.println(JSONObject.toJSONString(getMethodVariableName("com.minipika.mapper.UserMapper", "findUser")));
  }

  public static String[] getMethodVariableName(String classname, String methodname) {
    try {
      ClassPool pool = ClassPool.getDefault();
      ClassClassPath path = new ClassClassPath(user_mapper.class);
      pool.insertClassPath(path);
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

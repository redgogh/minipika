package method.test;

/*
 * Creates on 2019/11/13.
 */

import com.alibaba.fastjson.JSONObject;

/**
 * @author lts
 */
public class method_test {

  public static void main(String[] args) {
    String[] names = method_variable.getMethodVariableName("method.test.MethodTest", "test");
    System.out.println(JSONObject.toJSONString(names));
  }

  public String test(String a, String b, Object user) {
    return "hello";
  }

  public static String test2(String a, String b, Object user) {
    return "hello";
  }

  public static String test3(String p1, String p2, Object user) {
    return "hello";
  }

}

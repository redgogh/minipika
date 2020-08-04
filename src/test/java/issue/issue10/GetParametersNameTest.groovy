package issue.issue10


import mapper.UserMapper

/*
 * Creates on 2019/11/13.
 */

import org.junit.Test

import java.lang.reflect.Method

/**
 * @author lts
 */
class GetParametersNameTest
{

  /**
   * 测试获取方法参数名
   */
  @Test
  void methods() {
    long start = System.currentTimeMillis()
    Method method = UserMapper.methods[0]
    println Methods.getParameterNames(method)
    long end = System.currentTimeMillis()
    println end - start
  }

}

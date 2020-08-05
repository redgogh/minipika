package task

import com.alibaba.fastjson.JSON;

/*
 * Creates on 2019/11/13.
 */

import org.codehaus.groovy.control.CompilerConfiguration
import org.jiakesimk.minipika.framework.factory.Factorys
import org.junit.Test

import java.lang.reflect.Method

/**
 * @author lts
 */
class GroovyTask {

  UserMapper mapper = Factorys.forMapper(UserMapper)

  /**
   * 132
   * 测试查询
   */
  @Test
  void task() {
    long start = System.currentTimeMillis()
    System.out.println(JSON.toJSONString(mapper.findUser("key1")))
    System.out.println(JSON.toJSONString(mapper.findUserList("name3")))
    long end = System.currentTimeMillis()
    println end - start
  }

  @Test
  void initMapper() {
    long start = System.currentTimeMillis()
    UserMapper userMapper = Factorys.forMapper(UserMapper) // 计算第一次初始化耗时
    long end = System.currentTimeMillis()
    println end - start

    start = System.currentTimeMillis()
    PhoneMapper phoneMapper = Factorys.forMapper(PhoneMapper) // 计算第二次初始化耗时
    end = System.currentTimeMillis()
    println end - start

    start = System.currentTimeMillis()
    MemberMapper memberMapper = Factorys.forMapper(MemberMapper) // 计算第三次初始化耗时
    end = System.currentTimeMillis()
    println end - start
  }

  /**
   * 24
   * 测试在编译不保存抽象类真实参数名的情况下应该如何
   * 获取抽象类的真实参数名。
   */
  @Test
  void javaSourcePath() {
    long start = System.currentTimeMillis()
    println System.getProperty("user.dir")
    println UserMapper.classLoader.getResource("")
    println System.getProperty("java.class.path")
    println "--------------------------------------"
    System.getProperties().each {
      println it
    }
    println "--------------------------------------"
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader()
    CompilerConfiguration configuration = new CompilerConfiguration()
    configuration.parameters = true
    GroovyClassLoader loader = new GroovyClassLoader(classLoader, configuration)
    Class clazz = loader.parseClass(new File("src/main/java/task/UserMapper.groovy"))
    clazz.methods.each {
      println it
      it.parameters.each { p ->
        println p
      }
    }
    long end = System.currentTimeMillis()
    println end - start
  }

  /**
   * 判断一个类是不是groovy class
   */
  @Test
  void groovyString() {
    Class clazz = UserMapper.class
    println "a/${clazz.package.name}/b"
  }

  /**
   * 16
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

  @Test
  void thisPlus() {
    println getClass().getName() + "@" + Integer.toHexString(hashCode())
  }

}

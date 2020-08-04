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
    int start = System.currentTimeMillis()
    System.out.println(JSON.toJSONString(mapper.findUser("update1")))
    System.out.println(JSON.toJSONString(mapper.findUser("name2")))
    System.out.println(JSON.toJSONString(mapper.findUser("name3")))
    System.out.println(JSON.toJSONString(mapper.findUser("name4")))
    System.out.println(JSON.toJSONString(mapper.findUser("name5")))
    int end = System.currentTimeMillis()
    println end - start
  }

  /**
   * 24
   * 测试在编译不保存抽象类真实参数名的情况下应该如何
   * 获取抽象类的真实参数名。
   */
  @Test
  void javaSourcePath() {
    int start = System.currentTimeMillis()
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
    int end = System.currentTimeMillis()
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
    int start = System.currentTimeMillis()
    Method method = UserMapper.methods[0]
    println Methods.getParameterNames(method)
    int end = System.currentTimeMillis()
    println end - start
  }

}

package task;

/*
 * Creates on 2019/11/13.
 */

import com.alibaba.fastjson.JSON
import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.Test;

/**
 * @author lts
 */
class GroovyTask {

  /**
   * 测试查询
   */
  @Test
  void task() {
    UserMapper mapper = UserMapper.mapper;
    System.out.println(JSON.toJSONString(mapper.findUser("update1")));
  }

  /**
   * 测试在编译不保存抽象类真实参数名的情况下应该如何
   * 获取抽象类的真实参数名。
   */
  @Test
  void javaSourcePath() {
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
  }

}

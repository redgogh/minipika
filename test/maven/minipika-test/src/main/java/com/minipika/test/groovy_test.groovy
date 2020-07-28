package com.minipika.test

import com.minipika.mapper.UserMapper
import org.junit.Test

class groovy_test {

  @Test
  void test() {
    def parent = UserMapper.classLoader
    def classloader = new GroovyClassLoader(parent)
    def gclass = classloader.parseClass(new File("src/main/java/com/minipika/mapper/UserMapper.groovy"))
  }

}

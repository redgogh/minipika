package com.minipika

import groovyjarjarasm.asm.ClassReader
import groovyjarjarasm.asm.ClassVisitor
import groovyjarjarasm.asm.Label
import groovyjarjarasm.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Type;

/*
 * Creates on 2019/11/13.
 */

import org.junit.Test

/**
 * @author lts
 * @email ltsloveyellow@aliyun.com
 */
class ASMTest {

  @Test
  void test1() {
    ClassReader reader = new ClassReader("com.minipika.mapper.UserMapper");
    println ""
  }

}

package com.minipika.mapper;

/*
 * Creates on 2019/11/13.
 */

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation

/**
 * @author lts
 */
@GroovyASTTransformation(phase = CompilePhase.CANONICALIZATION)
class GroovyAstTest implements ASTTransformation {

  @Override
  void visit(ASTNode[] nodes, SourceUnit source) {
    nodes.each {
      println it
    }
    println source.AST
    println source.source.reader.text
  }

}

class MyTest {

  static def main(args) {
    def parent = MyTest.classLoader
    def loader = new GroovyClassLoader(parent)
    def gclass = loader.parseClass(new File("src/main/java/com/minipika/mapper/UserMapper.groovy"))
  }

}


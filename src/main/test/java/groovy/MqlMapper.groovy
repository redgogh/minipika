package groovy

import groovyjarjarasm.asm.tree.ClassNode
import groovyjarjarasm.asm.tree.FieldNode
import groovyjarjarasm.asm.tree.MethodNode
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.GroovyClassVisitor
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.jiakesimk.minipika.components.annotation.SQL
import org.jiakesimk.minipika.components.mql.MqlBuilder

interface MqlMapper {

  @SQL("""
    select * from minipika_user where 1=1
    #if INE(user.name) && user.name != null
      and username = #{user.name}
    #end
    and wdnmd = #{wdnmd}
  """)
  def findUser(User user, String wdnmd)

  @SQL("""
    insert into user (username) values (#{user.name})
  """)
  def addUser(User user)

}

@GroovyASTTransformation
class Test implements ASTTransformation {

  @Override
  void visit(ASTNode[] nodes, SourceUnit source) {
    source.each {
      it.visitContents(new GroovyClassVisitor() {
        @Override
        void visitClass(org.codehaus.groovy.ast.ClassNode node) {
          println node.name
        }

        @Override
        void visitMethod(org.codehaus.groovy.ast.MethodNode node) {
          println node.name
        }

        @Override
        void visitField(org.codehaus.groovy.ast.FieldNode node) {
          println node.name
        }

        @Override
        void visitConstructor(ConstructorNode node) {
          println node.name
        }

        @Override
        void visitProperty(PropertyNode node) {
          println node.name
        }
      })
    }
  }

  @org.junit.Test
  void ast() {
    MqlMapper.class
  }

  @org.junit.Test
  void test() {
    MqlBuilder m = new MqlBuilder(MqlMapper.class)
    User user = new User()
    user.name = "123"
    println m.invoke("findUser", user, "XXX")
    println m.invoke("addUser", user)
  }

}
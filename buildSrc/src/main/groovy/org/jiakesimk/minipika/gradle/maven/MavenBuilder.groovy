package org.jiakesimk.minipika.gradle.maven

import groovy.xml.QName

class MavenBuilder {

  /**
   * 删除节点
   *
   * @param asNode 根节点
   */
  static void removeNode(asNode, name) {
    def node = null
    asNode.value().each {
      if (it.name().localPart == name) node = it
    }
    if (node != null) asNode.remove(node)
  }

  /**
   * 创建Dependencies节点并添加依赖
   *
   * @param asNode 根节点
   * @param implementations 依赖信息
   */
  static void addDependencies(asNode, implementations) {
    def dependencies = asNode.appendNode("dependencies")
    implementations.each { implementation ->
      def dependencyName = implementation.name
      if (dependencyName != 'unspecified') {
        def dependency = dependencies.appendNode("dependency")
        dependency.appendNode('groupId', implementation.group)
        dependency.appendNode('artifactId', dependencyName)
        dependency.appendNode('version', implementation.version)
        if (dependencyName == 'groovy-all') {
          dependency.appendNode('type', 'pom')
        }
      }
    }
  }

  static void addPluginNode(asNode, Closure closure) {
    def buildNode = exist(asNode, 'build')
    def pluginsNode = exist(buildNode, 'plugins')
    closure.call(pluginsNode.appendNode('plugin'))
  }


  /**
   * 获取Build节点
   *
   * @param asNode 根结点
   * @return build节点
   */
  static Node exist(asNode, nodeName) {
    def children = asNode.children()
    def node = null
    children.each {
      if (nameEq(it, nodeName)) node = it
    }
    node != null ? node : asNode.appendNode(nodeName) as Node
  }

  static boolean nameEq(node, compare) {
    if (node instanceof Node) {
      def name = node.name()
      if (name instanceof QName) {
        name = name.localPart
      }
      return name == compare
    } else {
      return false
    }
  }

}

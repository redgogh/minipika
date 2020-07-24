package org.jiakesimk.minipika.gradle.maven

abstract class MavenBuilder {

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

  /**
   * 添加POM插件
   *
   * @param node 根节点
   * @param closure 闭包
   */
  static void addPlugins(asNode, Closure<PluginNode> pluginClosure) {
    pluginClosure.call()
  }

  static PluginNode plugin(Closure<PluginNode> pluginClosure) {}

  class PluginNode {

  }

}

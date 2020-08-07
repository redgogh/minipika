package org.jiakesiws.minipika.gradle.maven

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

  /**
   * 添加plugin节点
   *
   * @param asNode 根节点
   * @param closure 闭包
   */
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

  /**
   * 节点名称比较
   *
   * @param node 节点对象
   * @param compare 比较名称
   * @return true/false
   */
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

  @SuppressWarnings("all")
  static void buildMaven(implementations, asNode) {
    // 删除原有的dependencies节点
    removeNode(asNode, 'dependencies')
    addDependencies(asNode, implementations)
    // 添加maven-compiler-plugin插件
    addPluginNode(asNode) {
      it.appendNode('groupId', 'org.apache.maven.plugins')
      it.appendNode('artifactId', 'maven-compiler-plugin')
      def configuration = it.appendNode('configuration')
      configuration.appendNode('compilerArgs', '-parameters')
    }
    // 添加gmavenplus插件
    addPluginNode(asNode) {
      it.appendNode('groupId', 'org.codehaus.gmavenplus')
      it.appendNode('artifactId', 'gmavenplus-plugin')
      it.appendNode('version', '1.9.0')
      def goals = it.appendNode('executions').appendNode('execution').appendNode('goals')
      goals.appendNode('goal', 'addSources')
      goals.appendNode('goal', 'addTestSources')
      goals.appendNode('goal', 'generateStubs')
      goals.appendNode('goal', 'compile')
      goals.appendNode('goal', 'generateTestStubs')
      // 设置编译时保留参数名
      def configuration = it.appendNode('configuration')
      configuration.appendNode('parameters', 'true')
      def source = configuration.appendNode('sources').appendNode('source')
      source.appendNode('directory', '${project.basedir}/src/main/')
      source.appendNode('includes').appendNode('include', '**/*.groovy')
    }
  }

}

# Poseidon ORM Framework

> PoseidonFramework是一个非常简单且立马可以上手的ORM框架。它提供了表和对象之前的映射，以及封装了常用的增删改查等操作，并且非常轻。除此之外PoseidonFramework还支持我测过最高的是一万次并发

[API文档-使用手册](https://github.com/PageNotFoundx/poseidon/blob/master/api/v1.0/README.md)

# 安装

由于当前项目并没有发布到Maven的中央仓库上，所以暂时还不能从中央仓库上面导入它，所以我们需要进行手动的打包安装。当然Maven提供了这些功能，安装非常简单。

**第一步先将项目check到本地**

```
git clone https://github.com/PageNotFoundx/poseidon.git
```

**然后将项目安装到本地仓库**

```java
mvn install
```

或者使用idea的maven插件安装

!###### [maveninstallbyidea](https://github.com/PageNotFoundx/poseidon/blob/master/description/mavenisntall.png)

# 在其他项目导入依赖

```xml
<dependency>
    <groupId>com.keyboard</groupId>
    <artifactId>poseidon</artifactId>
    <version>1.0.GYS</version>
</dependency>
```

**最后愉快的开始写代码吧！**

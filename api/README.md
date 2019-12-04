# 使用文档

> API手册

# 配置文件搭建

TractorFramework会自动扫描**resources**目录下的**tractor.properties**,如果没有这个文件那么会报一个**ReadException**

如果配置文件的文件名是自定义的，那么需要调用一个手动加载配置的方法**ManualConfig.load()**，示例如下:

```java
// 假设配置文件的名字叫做"newtractor.properties"
ManualConfig.load("newtractor.properties");
```

这个需要在最开始就调用，比如springboot的启动类在启动前调用，或者tomcat的init等，需要在使用前调用。

**配置项**

```properties
#####################################
### 数据库属性
#####################################
tractor.jdbc.url = jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT
tractor.jdbc.driver = com.mysql.cj.jdbc.Driver
tractor.jdbc.username = root
tractor.jdbc.password = root

#####################################
### 是否开启事物，默认为false
#####################################
tractor.jdbc.transaction = true

#####################################
### 连接池大小
### 默认最小连接为2个连接
### 默认最大连接为6个连接
#####################################
tractor.connectionPool.minSize = 2
tractor.connectionPool.maxSize = 90

# 表名前缀
tractor.model.prefix = kkb
# 模型所在的包
tractor.model.package = com.tractor.model.experiment

```

----

# 模型映射

> Tractor提供了Table和Model之间的映射，创建表和新增字段的时候只需要在Model中新增即可。由TractoFramework来创建表，以及更新字段，不需要开发人员建立完表之后又去建立Model（索引需要手动建立）。

**提供的注解**

- @Model

    **@Model**注解的范围在**TYPE**内，将注解放在类上即代表这是一个模型类。Model注解有两个参数分别为：**value**和**engine**，engine默认值为**InnoDB**

- @Ignore

    **@Ignore**注解范围在**FIELD**内，将注解放在在字段上代表该字段不和数据库进行交互动作。

- @Column

    **@Column**注解范围在**FIELD**内，代表字段的一些属性。参数为 **String value();** 假设我当前有个**private String name**字段，然后注解上传入参数 **@Column("varchar(255) not null")**。其实这就相当于省下了字段名。

- @Increase

    **@Increase**注解范围在**FIELD**内，代表被注解的字段会进行自增。

- @Comment

    **@Comment**字段注释

- @PrimaryKey

    **@PrimaryKey**主键

具体Model的实现可以参考一下本项目下**com.tractor.model.experiment**包下的Model。

----

# Jdbc操作

### **获取JdbcSupport**

如果你是**service**类的话推荐使用方式是继承**JdbcSupport**，示例：

```java
public class UserServiceImpl 
extends JdbcSupport implements UserService{
    // 这里调用JdbcSupport中提供的API
}
```

如果是测试类的话我推荐使用 **getTemplate()** 的方式来进行Jdbc操作，示例：

```java
public class Main{

    // 获取JdbcSupport对象
    static JdbcSupport jdbc = JdbcSupport.getTemplate();

    public static void main(String[] args){
      // 省略操作...
    }
    
}
```

----

### **JdbcSupport提供的API**

> JdbcSupport提供了很多常用的API供开发人员调用

**查询单个对象并返回**

```java
/**
 * 查询并返回对象
 * @param sql sql语句
 * @param obj 需要返回的对象class
 * @param args 参数
 * @param <T>
 * @return
 */
<T> T queryForObject(String sql, Class<T> obj, Object... args);
```

**示例：**

*假设我们当前有一张user_model表，我们要查询单条数据*

```java
public class Main{

    // 获取JdbcSupport对象
    static JdbcSupport jdbc = JdbcSupport.getTemplate();

    public static void main(String[] args){
        String sql = "select * from user_model where user_name = ?";
        UserModel user = jdbc.queryForObject(sql,UserModel.class,"张三");
    }
    
}
```

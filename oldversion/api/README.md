# 使用文档

> API手册

# 配置文件搭建

Minipika Framework会自动扫描**resources**目录下的**minipika.properties**,如果没有这个文件那么会报一个**ReadException**

如果配置文件的文件名是自定义的，那么需要调用一个手动加载配置的方法**ConfigLoader.loadConfig()**，示例如下:

```java
// 假设配置文件的名字叫做"newminipika.properties"
ConfigLoader.loadConfig("newminipika.properties");
```

这个需要在最开始就调用，比如springboot的启动类在启动前调用，或者tomcat的init等，需要在使用前调用。

**配置项**

```properties
#####################################
### 数据库属性
#####################################
minipika.jdbc.url = jdbc:mysql://127.0.0.1:3306/remotely?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT
minipika.jdbc.driver = com.mysql.cj.jdbc.Driver
minipika.jdbc.username = root
minipika.jdbc.password = root

#####################################
### 是否开启事物，默认为false
#####################################
minipika.jdbc.transaction = true

#####################################
### 是否开启缓存
#####################################
minipika.jdbc.cache = true

#####################################
### 缓存过期时间，以秒为单位，支持加减乘除表达式
### 提供了时间变量可配合表达式使用
### second（秒）、minute（分）、
### hour（时）、day（天）、week（周）
#####################################
minipika.jdbc.refresh = hour * 1

#####################################
### 连接池大小
#####################################
minipika.connectionPool.minSize = 2
minipika.connectionPool.maxSize = 90

# 前缀
minipika.entity.prefix = heitui
# entity所在的包
minipika.entity.package = com.raniaia.modules.provide.entity
# builder xml模板文件所在的路径
minipika.entity.mapper = com.raniaia.modules.provide.entity.mapper

# 配置norm.json文件路径
minipika.norm.json = norm.json


```

----

# 模型映射

> Minipika提供了Table和Entity之间的映射，创建表和新增字段的时候只需要在Entity中新增即可。由框架来自动创建表，以及更新字段，不需要开发人员建立完表之后又去建立Entity（索引需要手动建立）。

**提供的注解**

- @Entity
> @Entity注解的范围在**TYPE**内，将注解放在类上即代表这是一个模型类。@Entity注解有三个参数分别为：**value**、**engine**、**increment**，它们分别代表名、引擎、自增长从多少开始。

- @Ignore
> **@Ignore**注解范围在**FIELD**内，将注解放在在字段上代表该字段不和数据库进行交互动作。

- @Column
> **@Column**注解范围在**FIELD**内，代表字段的一些属性。参数为 **String value();** 假设我当前有个**private String name**字段，然后注解上传入参数 **@Column("varchar(255) not null")**。其实这就相当于省下了字段名。

- @Increase
> **@Increase**注解范围在**FIELD**内，代表被注解的字段会进行自增。

- @Comment
> **@Comment**字段注释

- @Pk
> **@Pk**主键

具体Entity的实现可以参考一下本项目下的 [ExampleEntity](https://github.com/Laniakeamly/minipika/blob/master/src/main/groovy/org/minipika/experiment/ExampleEntity.java)。

当Entity配置好了之后在启动时会自动创建表和字段。

----

# Jdbc操作

### **获取JdbcSupport**

获取JdbcSupport需要通过框架提供的[BeansManager#getBean](https://github.com/Laniakeamly/minipika/blob/master/src/main/java/org/raniaia/minipika/framework/beans/BeansManager.java)对象来获取。

示例：

```java
public class UserServiceImpl implements UserService{
    // 获取JdbcSupport实例
    JdbcSupport jdbc = MinipikaApplication.getBean("jdbc");
}
```

----

### **查询单个对象并返回**

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

*假设我们当前有一张user_entity表，我们要查询单条数据*

```java
public class Main{

    // 获取JdbcSupport对象
    static JdbcSupport jdbc = JdbcSupport.getTemplate();

    public static void main(String[] args){
        String sql = "select * from user_entity where user_name = ?";
        UserEntity user = jdbc.queryForObject(sql,UserEntity.class,"张三");
        // 这里的话queryForList也是一样的，没什么太大的区别。
        List<UserEntity> user = jdbc.queryForList(sql,UserEntity.class,"张三");
    }
    
}
```

----

### **分页查询**

```java
/**
 * 分页查询,SQL不用加limit
 * @param sql
 * @param args
 * @return
 */
NativePageHelper queryForPage(String sql, NativePageHelper pageVo, Object... args);
```

分页查询需要传入一个**NativePageHelper**对象的分页插件，**NativePageHelper**是一个抽象类，我们不会使用它。我提供了一个叫做**PageHelper**的对象，它继承自**NativePageHelper**。所以我们分页都用它来传参。

**示例代码：**

*假设我们要查询**user_entity**这张表的**user_name**字段，查询10到20条之间的数据。*

```java
//
// 第一步需要new一个PageHelper插件
//
PageHelper pageVo = new PageHelper(UserEntity.class); // 构造函数需要返回结果的类型
// 设置页码和页面大小，这两个可以通过构造函数传入。
pageVo.setPageNum(2);     
pageVo.setPageSize(10);

//
// 第二步编写SQL
//
String sql = "select `user_name` from user_entity";

//
// 第三步执行查询
//
jdbc.queryForPage(sql,pageVo);

//
// 获取查询到的数据
// pageVo查询完后还包含总页数、总记录数，当前第几页、以及每页显示多少条数据等这些基本信息
//
System.out.println(JSON.toJSONString(pageVo.getData()));
```

值得注意的是我们并不需要在sql上添加limit 2,10。

NativePageHelper是为了扩展而将它写成了抽象类，大家可以去继承它然后实现自己的东西。

---

### **更新和新增**

**update**

update其实非常简单，提供了一下三种方法：
```java

    /**
     * 更新所有实体类中的所有数据，但不包括为空的数据。
     * @param obj 实体类
     * @return 更新条数
     */
    int update(Object obj);

    /**
     * 通过SQL语句来更新数据。
     * @param sql sql语句
     * @param args 参数列表
     * @return 更新条数
     */
    int update(String sql, Object... args);

    /**
     * 传入一个实体类，将实体类中为空的数据也进行更新。
     * @param obj 实体类
     * @return 更新条数
     */
    int updateDoNULL(Object obj);

```

第一个是更新传入实体对象更新，但是不会更新实体中为空的数据。

第二个是通过sql来更新。

第三个也是更新传入的实体对象，但是它会更新为null的数据。

除了第二个，其他两个都是根据主键进行更新的。

**insert**

至于insert的话都是一样，我这就不细讲了。

---

## 使用XML模板

> XML模板是Minipika框架提供的一个动态SQL的功能，他能帮你简化代码以及管理。

先看一个简单的动态SQL模板例子吧。

例子：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mapper name="findProductPric">
    <select name="findProductName" result="math(BigDecimal)">
        select product_amount from kkb_product_entity where id = {{id}}
    </select>
</mapper>
```
这里面就可以看出来和MyBatis中的一些不同。比如我们的Result。这边的Result采用的不是全类名，我将他简化成了包名加类名。如果你要返回String结果那么使用lang(String)即可。

如果result对象是Entity呢？
那就更简单了，比如我要return一个ProductEntity。那么我们直接这样写即可：
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mapper name="findProductPric">
    <select name="findProduct" result="ProductEntity">
        select * from kkb_product_entity where id = {{id}}
    </select>
</mapper>
```
不需要加上全类名，因为在配置文件中我已经知道了你Entity类放在哪个位置了。框架会自动帮你添加上。

### if操作 

> 要说动态SQL重要的是什么呢？那就是if这些操作。那么我们看下在这个框架if操作是如何实现的吧!

假设我们这边要根据需求进行动态查询。
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mapper name="findProduct">
    <select name="findProduct" result="ProductEntity">
        select * from kkb_product_entity where 1=1
        <if groovy="id != null">
            and id = {{id}}
        </if>
        <if groovy="name != null">
            and name = {{name}}
        </if>
        <if groovy="status != null">
            and status = {{status}}
        </if>
    </select>
</mapper>
```
这样就可以进行动态查询了，当然这种方式太繁琐了。我并不推荐大家用这种方法。minipika还提供了一种更简单的方法。
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mapper name="findProduct">
    <select name="findProduct" result="ProductEntity">
        select * from kkb_product_entity where 1=1
        <if groovy="$req != null">
            <cond>and id = {{id}}</cond>
            <cond>and name = {{name}}</cond>
            <cond>and status = {{status}}</cond>
        </if>
    </select>
</mapper>
```
如果需要使用else的话需要用choose标签
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mapper name="findProduct">
    <select name="findProduct" result="ProductEntity">
        select * from kkb_product_entity where 1=1
        <choose>
            <if groovy="$req != null">
                <cond>and id = {{id}}</cond>
                <cond>and name = {{name}}</cond>
                <cond>and status = {{status}}</cond>
            </if>
            <else>
                <cond>and id1 = {{id1}}</cond>
                <cond>and name1 = {{name1}}</cond>
                <cond>and status1 = {{status1}}</cond>
            </else>
        </choose>
    </select>
</mapper>
```
这个else中的cond是根据顺序的判断的，也就是说if第一个cond的else在else标签下也必须是第一个cond标签。

### 批量插入
```xml
<insert name="insertUserEntity">
    INSERT INTO `user_entity`(
    `google_email`,
    `password`,
    `user_age`,
    `uuid`,
    `product_name`,
    `create_time`,
    `user_name`,
    `address`
    )
    VALUES (
    'c28a7745-7508-48a0-820e-a5cd14748d24',
    '123456',
    18,
    'c28a7745-7508-48a0-820e-a5cd14748d24',
    'c28a7745-7508-48a0-820e-a5cd14748d24',
    '敌敌畏',
    '2019-12-03 09:39:19',
    ?,?,{{username}}
    );
    <foreach index="index" item="user" collections="users">
        <parameter>{{user.userName}},{{user.address}}</parameter>
    </foreach>
</insert>
```
这里使用foreach即可，需要注意的是参数需要加上问号，然后在foreach内部使用parameter标签将参数放到parameter标签中用逗号隔开即可。

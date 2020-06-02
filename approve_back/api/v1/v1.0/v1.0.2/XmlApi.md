# SQL Mapper文件搭建

> v1.0.2版本新增的内容

**首先我们需要在minipika.properties配置文件中新增一条配置**

```properties
# mapper xml文件所在的位置
inject.entity.mapper = org.raniaia.inject.mapper
```

# mapper xml

mapper xml目前提供了以下标签：

- mapper
- select
- insert
- update
- choose
- if
- else
- foreach
- cond

# mapper

mapper标签是根节点，最顶级的标签：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mapper name="userDao">
    <!-- mapper标签 -->
</mapper>
```

mapper标签有个**name**属性，这个属性代表了整个xml。它是唯一的，在全局不可重复。相当于类名一样(实际上就是类名)

---

## select

看完mapper标签后再来看看select标签，这个标签代表一个查询SQL。

有两个属性值：

- name
    
    name代表当前mapper的名称，和Java中的方法名一个性质。在mapper文件中是唯一的不可重复。

- result

    result代表返回结果

### 示例

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<mapper name="userDao">

    <!--
        mapper标签下的内容。
        以下内容表示从kkb_user_entity表中根据uuid查询
        result中是返回结果，这个返回结果可以不用加全类名。
        但条件是这个Entity类必须在配置文件中配置的路径下。
        配置项为：inject.entity.package
    -->
    <mapper name="findUserByUUID" result="UserEntity">
        select * from kkb_user_entity where uuid = {{uuid}}
    </mapper>

</mapper>
```

表示参数的语法是使用两个花括号中间传入参数名 **{{uuid}}**

---

# choose、if、else

if标签是动态sql中非常重要的一个逻辑判断语句，他能够帮助程序员构建动态sql，那么现在来看下怎么使用吧。

if标签中有一个属性叫**test**，这个属性代表你的判断条件。

```xml
<!--
    在test属性中参数名不需要加花括号引起来，可以直接填写。
    由于xml限制 <、>、&需要转义，这个需要注意。
    这段话解析成Java代码就是
    if(uuid != null){
        sql += uuid = ?
    }    
    这样看就很简单了对吧。
-->
<if test="uuid != null">
    uuid = {{uuid}}
</if>
```

其实if标签还不止这个功能，我还提供了一个非常方便的内置变量"$req"。假设我们当前有个SQL需要穿很多参数，为空的就不传平常我们都是怎么写的呢？相信大家像我以下这样写的吧。

假设我们当前sql语句是：

```sql
select * 
from user
-- 1=1大法好！
where 1=1
and username = 'zs'
and password = 'pw'
and user_age = 12
and user_friendId = 001
```

如果这些参数需要不为空就传入的话那么代码一定是这样写的。
```java
if(username != null){
    sql += "and username = ?";
}
if(password != null){
    sql += "and password = ?";
}
if(userAge != null){
    sql += "and userAge = ?";
}
if(userFriendId != null){
    sql += "and userFriendId = ?";
}
```
如果我猜得没错的话肯定是这样写的吧，即便是mybatis也是一个一个if标签对吗？

然而minipika有提供了一个内置参数，可以让你简写if，请看：

```xml
select * 
from user
where 1=1
<if test="$req != null">
    <cond>and username = {{username}}</cond>
    <cond>and password = {{password}}</cond>
    <cond>and userAge = {{userAge}}</cond>
    <cond>and userFriendId = {{userFriendId}}</cond>
</if>
```
这样就实现了和上面Java代码一毛一样的功能。是不是很简单。"$req"代表的是所有cond标签中的参数，如果有添加$req那么就代表给这些cond标签添加了一个逻辑判断。

大家这边肯定就有一个问题了，那我else怎么写？莫慌，else需要在choose标签中编写。
```xml
select * 
from user
where 1=1
<choose>
    <if test="$req != null">
        <cond>and username = {{username}}</cond>
        <cond>and password = {{password}}</cond>
        <cond>and userAge = {{userAge}}</cond>
        <cond>and userFriendId = {{userFriendId}}</cond>
    </if>
    <else>
        <cond>and username = 'zs'</cond>
        <cond>and password = 'ls'</cond>
        <cond>and userAge = '18'</cond>
        <cond>and userFriendId = '001'</cond>
    <else>
</choose>
```

这个else中的cond是根据顺序的判断的，也就是说if第一个cond的else在else标签下也必须是第一个cond标签。

---

# foreach

foreach也是一个非常常用的标签，所以我也加入了xml文件的标签队列中。

先看下我之前写foreach标签解析的时候写的一段xml代码
```xml
<mapper name="insertUsers">
    <!--
        for(int i=0,len=users.size; i<len; i++){
            sql.append("insert into user(name,sex) values ({item.name}},{{item.sex}});");
        }
    -->
    foreach =========================S
    <foreach index="index" item="item" collections="users">
        insert into user(name,sex) values ({{item.name}},{item.sex}});
        <choose>
            <if test="$req != username">
                and user_a = {{usera}}
            </if>
            <else>
                and user_b = {{userb}}
            </else>
        </choose>
    </foreach>
    foreach =========================E
</mapper>
```

foreach标签有三个属性

- index

    代表当前循环到第几个了，就像是Java中for循环的i变量

- item

    item代表当前循环对象

- collections

    代表需要循环的List

# 批量插入

```xml
<insert name="addProducts">
    INSERT INTO `product_entity`(`product_name`, `uuid`) VALUES (?,?);
    <foreach item="product" collections="products">
        <parameter>{{product.productName}},{{product.uuid}}</parameter>
    </foreach>
</insert>
```
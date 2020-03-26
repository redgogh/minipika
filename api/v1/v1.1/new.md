# 新功能

- 添加字段约束
- 配置多个model和mapper路径
- 字段检测，动态修改字段
- 事务管理器
- 支持所有模板引擎，并开放模板引擎编写脚本
- 可能会整合spring
- 开放SQL解析器

**和分布式有关**

- 中央缓存系统
- 分布式配置系统

---

# 添加字段约束

在创建字段的时候可以设置字段的规则
比如说我现在想创建一个email字段
```java
@Column("varchar(255) not null")
private String email;
```
我可以把这个字段配置正则表达式只允许为邮箱格式
```java
@Column("varchar(255) not null")
@Norm("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")
private String email;
```

# 可配置多个model和mapper路径

```properties

# model所在的包
approve.model.package[0] = org.raniaia.approve.experiment
approve.model.package[1] = org.raniaia.approve.experiment
approve.model.package[2] = org.raniaia.approve.experiment
approve.model.package[3] = org.raniaia.approve.experiment
# builder xml模板文件所在的路径
approve.model.mapper[0] = org.raniaia.approve.builder
approve.model.mapper[1] = org.raniaia.approve.builder
approve.model.mapper[2] = org.raniaia.approve.builder
approve.model.mapper[3] = org.raniaia.approve.builder

```

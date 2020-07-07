# Minipika ORM Framework

![language-java](https://img.shields.io/badge/Java-80%25-brightgreen)
![language-groovy](https://img.shields.io/badge/Groovy-20%25-brightgreen)
![buildTool](https://img.shields.io/badge/buildTool-gradle-blue)
![download](https://img.shields.io/badge/downloads-v2.0.0-green)
![jdk](https://img.shields.io/badge/jdk-v1.8-blue)


> Minipika ORM Framework是基于Java和Groovy开发的持久层框架, 它支持直接调用接口无需声明XML编写SQL。SQL的编写方式是基于注解进行编写的, 
但是它不像Java的那样在换行时需要使用加号进行拼接。 演示：

```groovy
@Select(value = """
  select * from website_user_info where 1=1
  #if INE(user.username) && user.username != null
    and username = #{user.username}
  #end
""", forList = User.class)
def findUser(User user)
```
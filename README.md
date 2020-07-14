# Minipika ORM Framework

![language-java](https://img.shields.io/badge/Java-80%25-brightgreen)
![language-groovy](https://img.shields.io/badge/Groovy-20%25-brightgreen)
![buildTool](https://img.shields.io/badge/buildTool-gradle-blue)
![download](https://img.shields.io/badge/downloads-v2.0.0-green)
![jdk](https://img.shields.io/badge/jdk-v1.8-blue)


> Minipika ORM Framework是基于Java和Groovy开发的持久层框架。它不用创建XML编写动态SQL, 并且可以直接调用接口进行持久层的调用。


创建dao接口演示: 
```groovy
@Select(value = """
  queryOf * from website_user_info where 1=1
  #if INE(user.username) && user.username != null
    and username = #{user.username}
  #end
""", forList = User.class)
def findUser(User user)
```

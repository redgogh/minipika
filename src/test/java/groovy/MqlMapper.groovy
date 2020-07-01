package groovy


import org.jiakesimk.minipika.components.annotation.SQL
import org.jiakesimk.minipika.components.mql.MqlBuilder

import org.junit.Test

interface MqlMapper {

  @SQL("""
    select * from minipika_user where 1=1
    #if INE(user.name) && user.name != null
      and username = #{user.name}
    #end
    and wdnmd = #{wdnmd}
  """)
  def findUser(User user, String wdnmd)

  @SQL("""
    insert into user (username) values (#{user.name})
  """)
  def addUser(User user)

  @SQL("""
    insert into user (username) values (?)
    #foreach user : users
      #{user.name}
    #end
  """)
  def addBatch(List<User> users)

}

class Test {

  @org.junit.Test
  void test() {
    User user = new User()
    user.name = "123"
    MqlBuilder m = new MqlBuilder(MqlMapper.class)
    println m.invoke("findUser", user, "XXX")
    println m.invoke("addUser", user)
  }

  @org.junit.Test
  void test2() {
    MqlBuilder m = new MqlBuilder(MqlMapper.class)
    List<User> users = [new User("name1"), new User("name2")]
    println m.invoke("addBatch", users)
  }

  @org.junit.Test
  void test3() {
    MqlBuilder m = new MqlBuilder(MqlMapper.class)
    Mapper mapper = m.bind()
    List<User> users = [new User("name1"), new User("name2")]
    println mapper.addBatch(users)
  }

}
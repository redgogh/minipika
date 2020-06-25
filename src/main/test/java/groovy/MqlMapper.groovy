package groovy

import org.jiakesimk.minipika.components.annotation.SQL
import org.jiakesimk.minipika.components.mql.MqlBuilder

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

}

class Test {

  @org.junit.Test
  void test() {
    MqlBuilder m = new MqlBuilder(MqlMapper.class)
    User user = new User()
    user.name = "123"
    println m.invoke("findUser", user, "XXX")
    println m.invoke("addUser", user)
  }

}
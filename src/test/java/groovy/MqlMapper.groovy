package groovy


import org.jiakesimk.minipika.components.annotation.SQL
import org.jiakesimk.minipika.components.mql.MqlBuilder
import org.junit.Test

class MqlMapper {

  @SQL("""
    select * from minipika_user where 1=1
    #if INE(user.name) && user.name != null
      and username = #{user.name}
    #end
    and wdnmd = #{wdnmd}
  """)
  def findUser(User user, String wdnmd) {}

  @SQL("""
    insert into user (username) values (#{user.name})
  """)
  def addUser(User user) {}

  @Test
  void test() {
    User user = new User()
    user.name = "123"
    MqlBuilder m = new MqlBuilder(MqlMapper.class)
    println m.invoke("findUser", user, "XXX")
    println m.invoke("addUser", user)
  }

}
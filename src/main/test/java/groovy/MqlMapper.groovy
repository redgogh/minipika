package groovy

import org.jiakesimk.minipika.components.annotation.StructuredQuery
import org.jiakesimk.minipika.components.mql.MqlBuilder
import org.junit.Test

class MqlMapper {

  @StructuredQuery("""
    select * from minipika_user where 1=1
    #if INE(user.name) && user.name != null 
      username = #user.name
    #end
  """)
  def findUser(User user, String wdnmd) {}

  @Test
  void test() {
    MqlBuilder m = new MqlBuilder(MqlMapper.class)
    User user = new User()
    user.name = "123"
    println m.invoke("findUser", user, "GNM")
  }

}
package groovy

import org.jiakesimk.minipika.components.annotation.StructuredQuery
import org.jiakesimk.minipika.components.mql.MqlBuilder
import org.junit.Test

class MqlMapper {

  @StructuredQuery("""
    select * from minipika_user where 1=1
    #if isNotEmpty(user.name) && user.name != null 
      username = #user.name
    #end
  """)
  def findUser(User user, String wdnmd) {}

  @Test
  void test() {
    new MqlBuilder(MqlMapper.class)
  }

}
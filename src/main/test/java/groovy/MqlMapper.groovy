package groovy

import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.mql.groovy.MqlProxy
import org.junit.Test

import java.lang.reflect.Method

class MqlMapper {

  @Select("""
    select * from user_info where 1=1
    #if isNotEmpty(user.name)
      and name = #user.name
    #end
    #if user.name != 0 && user.name != null
      and age > #user.age
    #end
    and money >= #user.money
  """)
  def findUser(User user) {}

  @Test
  void test() {
    Method method = this.class.getDeclaredMethod("findUser", User.class)
    println method.getParameters()
  }

}
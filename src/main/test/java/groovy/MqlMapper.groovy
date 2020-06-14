package groovy

import org.jiakesimk.minipika.components.annotation.Select

@Select("""
  select * from user_info where 1=1
  if(isNotEmpty(#user.name)){
    and name = #user.name
  }
  if(#user.name != 0 && #user.name != null) {
    and age > #user.age
  }
  and money >= #user.money
""")
def findUser(User user) {}
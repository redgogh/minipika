package jdbc

import entity.User
import org.jiakesiws.minipika.components.annotation.QueryOf

interface UserMapper
{

  @QueryOf("""
    select * from storage_user_info where age = #{age}
  """)
  List<User> queryUser(String age)

}
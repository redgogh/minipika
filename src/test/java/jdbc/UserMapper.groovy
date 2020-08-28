package jdbc

import entity.User
import org.jiakesiws.minipika.components.annotation.QueryOf

interface UserMapper
{

  @QueryOf("""
    select * from storage_user_info 
    where username like(%#{}name%)
    
  """)
  List<UserInfo> queryUser(String name)

}
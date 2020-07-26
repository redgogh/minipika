package a

import com.minipika.entity.user
import org.jiakesimk.minipika.components.annotation.Batch
import org.jiakesimk.minipika.components.annotation.QueryOf
import org.jiakesimk.minipika.components.annotation.Update
import org.jiakesimk.minipika.framework.factory.Factorys

interface UserMapper {

  UserMapper mapper = Factorys.forMapper(this)

  @QueryOf("""
    select * from website_user_info where 1=1
    #IF NOT_EMPTY(username)
      and username = #{username}
    #END
  """)
  user findUser(username)

  @QueryOf("""
    select * from website_user_info where 1=1
    #IF NOT_EMPTY(username)
      and username = #{username}
    #END
  """)
  List<user> findUserList(username)

  @Batch("""
    insert into website_user_info (username, `password`) values (?, ?) 
    #FOREACH user : users
      #{user.username},#{user.password}
    #END
  """)
  int[] addBatch(List<user> users, name)

  @Update("""
    update website_user_info set username = #{newName}
    where username = #{name}
  """)
  int update(name, newName)

}

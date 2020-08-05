package task

import org.jiakesimk.minipika.components.annotation.Batch
import org.jiakesimk.minipika.components.annotation.QueryOf
import org.jiakesimk.minipika.components.annotation.Update

interface MemberMapper {

  @QueryOf("""
    select * from website_user_info where 1=1
    #IF NOT_EMPTY(username)
      and username = #{username}
    #END
  """)
  User findUser(username)

  @QueryOf("""
    select * from website_user_info where 1=1
    #IF NOT_EMPTY(username)
      and username = #{username}
    #END
  """)
  List<User> findUserList(username)

  @QueryOf("""
    select * from website_user_info
  """)
  List<User> findAll()

  @Batch("""
    insert into website_user_info (username, `password`) values (?, ?) 
    #FOREACH user : users
      #{user.username},#{user.password}
    #END
  """)
  int[] addBatch(List<User> users, name)

  @Update("""
    update website_user_info set username = #{newName}
    where username = #{name}
  """)
  int update(name, newName)

  @Update("""
    UPDATE `website_user_info` SET `username` = #{user.username}, 
    `password` = #{user.password} WHERE `id` = #{id};
  """)
  def updateUser(id, user)

}

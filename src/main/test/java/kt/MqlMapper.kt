package kt

import org.jiakesimk.minipika.components.annotation.Batch
import org.jiakesimk.minipika.components.annotation.Select

interface MqlMapper {

  @Select(value = """
    select * from website_user_info where 1=1
    #IF INE(user.username) && user.username != null
      and username = #{user.username}
    #END
  """, forObject = User::class)
  fun findUser(user:User): User

  @Batch("""
    insert into website_user_info (username, `password`) values (?, ?)
    #FOREACH user : users
      #{user.username},#{user.password}
    #END
  """)
  fun addBatch(users:List<User>): IntArray

}

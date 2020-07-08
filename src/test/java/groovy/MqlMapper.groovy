package groovy

import org.jiakesimk.minipika.components.annotation.Batch
import org.jiakesimk.minipika.components.annotation.Insert
import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.annotation.Update
import org.jiakesimk.minipika.components.mql.MqlCallback
import org.jiakesimk.minipika.framework.factory.Factorys
import org.junit.Test

interface MqlMapper {

  @Select(value = """
    select * from website_user_info where 1=1
    #if INE(user.username) && user.username != null
      and username = #{user.username}
    #end
  """, forList = User.class)
  def findUser(User user)

  @Insert("""
    insert into website_user_info (username, `password`) 
    values (#{user.username},#{user.password})
  """)
  def addUser(User user)

  @Update("""
    UPDATE `website_user_info` SET `username` = #{user.username}, `password` = #{user.password} WHERE `id` = #{id};
  """)
  def updateUser(id, User user)

  @Batch("""
    insert into website_user_info (username, `password`) values (?, ?)
    #foreach user : users
      #{user.username},#{user.password}
    #end
  """)
  def addBatch(List<User> users)

  class ForGroovy {

    @Test
    void findUserTest() {
      def start = System.currentTimeMillis();
      MqlMapper mapper = Factorys.forMface(MqlMapper)
      println mapper.findUser(new User("name3", "pass3"))
      def end = System.currentTimeMillis();
      println end - start + "ms"
    }

    @Test
    void addUserTest() {
      User user = new User("name1")
      user.setUsername("key1")
      user.setPassword("value2")
      MqlMapper mapper = Factorys.forMface(MqlMapper)
      println mapper.addUser( new User("name2", "pass2"))
    }

    @Test
    void updateUserTest() {
      MqlMapper mapper = Factorys.forMface(MqlMapper)
      println mapper.updateUser(1, new User("update1", "update1"))
    }

    @Test
    void addBatchTest() {
      MqlMapper mapper = Factorys.forMface(MqlMapper)
      List<User> users = [new User("name3", "pass3"),
                          new User("name3", "pass3")]
      println mapper.addBatch(users)
    }

  }

}

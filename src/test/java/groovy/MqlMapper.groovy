package groovy

import org.jiakesimk.minipika.components.annotation.Batch
import org.jiakesimk.minipika.components.annotation.Insert
import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.annotation.Update
import org.jiakesimk.minipika.components.mql.MqlCallback
import org.junit.Test

interface MqlMapper {

  @Select(value = """
    select * from website_user_info where 1=1
    #if INE(user.username) && user.username != null
      and username = #{user.username}
    #end
  """, forList = User.class)
  def findUser(User user)

<<<<<<< HEAD
  @Update("""
    insert into website_user_info (`username`, `password`) 
    values (#{user.name}, #{user.password})
=======
  @Insert("""
    insert into website_user_info (username, `password`) 
    values (#{user.username},#{user.password})
>>>>>>> 63a9471409364789eed396591cec5f9c9d8e6dfe
  """)
  def addUser(User user)

  @Update("""
    UPDATE `website_user_info` SET `username` = #{user.username}, `password` = #{user.password} WHERE `id` = #{id};
  """)
  def updateUser(id, User user)

  @Batch("""
<<<<<<< HEAD
    insert into website_user_info (`username`, `password`) values (?, ?)
=======
    insert into website_user_info (username, `password`) values (?, ?)
>>>>>>> 63a9471409364789eed396591cec5f9c9d8e6dfe
    #foreach user : users
      #{user.username},#{user.password}
    #end
  """)
  def addBatch(List<User> users)

  class ForGroovy {

    @Test
    void findUserTest() {
      def start = System.currentTimeMillis();
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
      println mapper.findUser(new User("name1", "pass1"))
      def end = System.currentTimeMillis();
      println end - start + "ms"
    }

    @Test
    void addUserTest() {
      User user = new User("name1")
      user.setUsername("key1")
      user.setPassword("value2")
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
<<<<<<< HEAD
      println mapper.addUser(user)
=======
      println mapper.addUser( new User("name2", "pass2"))
    }

    @Test
    void updateUserTest() {
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
      println mapper.updateUser(1, new User("update1", "update1"))
>>>>>>> 63a9471409364789eed396591cec5f9c9d8e6dfe
    }

    @Test
    void addBatchTest() {
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
      List<User> users = [new User("name3", "pass3"),
                          new User("name3", "pass3")]
      println mapper.addBatch(users)
    }

  }

}

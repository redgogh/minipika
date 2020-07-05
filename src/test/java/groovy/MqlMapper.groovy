package groovy

import org.jiakesimk.minipika.components.annotation.Batch

import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.annotation.Update
import org.jiakesimk.minipika.components.mql.MqlCallback
import org.junit.Test

interface MqlMapper {

  @Select(value = """
    select * from website_user_info where 1=1
    #if INE(user.name) && user.name != null
      and username = #{user.name}
    #end
  """, forList = User.class)
  def findUser(User user)

  @Update("""
    insert into website_user_info (`username`, `password`) 
    values (#{user.name}, #{user.password})
  """)
  def addUser(User user)

  @Batch("""
    insert into website_user_info (`username`, `password`) values (?, ?)
    #foreach user : users
      #{user.name}
    #end
  """)
  def addBatch(List<User> users)

  class ForGroovy {

    @Test
    void findUserTest() {
      def start = System.currentTimeMillis();
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
      println mapper.findUser(new User("key"))
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
      println mapper.addUser(user)
    }

    @Test
    void addBatchTest() {
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
      List<User> users = [new User("name1"), new User("name2")]
      println mapper.addBatch(users)
    }

  }

}

package groovy

import org.jiakesimk.minipika.components.annotation.Batch

import org.jiakesimk.minipika.components.annotation.Select
import org.jiakesimk.minipika.components.annotation.Update
import org.jiakesimk.minipika.components.mql.MqlCallback
import org.junit.Test

interface MqlMapper {

  @Select("""
    select * from website_user_info where 1=1
    #if INE(user.name) && user.name != null
      and username = #{user.name}
    #end
  """)
  def findUser(User user)

  @Update("""
    insert into user (username) values (#{user.name})
  """)
  def addUser(User user)

  @Batch("""
    insert into user (username) values (?)
    #foreach user : users
      #{user.name}
    #end
  """)
  def addBatch(List<User> users)

  class ForGroovy {

    @Test
    void findUserTest() {
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
      println mapper.findUser(new User("key"))
    }

    @Test
    void addUserTest() {
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
      println mapper.addUser(new User("name"))
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

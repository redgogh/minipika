package groovy


import org.jiakesimk.minipika.components.annotation.SQL
import org.jiakesimk.minipika.components.mql.MqlCallback
import org.junit.Test

interface MqlMapper {

  @SQL("""
    select * from minipika_user where 1=1
    #if INE(user.name) && user.name != null
      and username = #{user.name}
    #end
    and age = #{age}
  """)
  def findUser(User user, int age)

  @SQL("""
    insert into user (username) values (#{user.name})
  """)
  def addUser(User user)

  @SQL("""
    insert into user (username) values (?)
    #foreach user : users
      #{user.name}
    #end
  """)
  def addBatch(List<User> users)

  def fuck()

  class ForGroovy {

    @Test
    void findUserTest() {
      MqlCallback m = new MqlCallback(MqlMapper)
      MqlMapper mapper = m.bind()
      println mapper.findUser(new User("123"), 18)
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

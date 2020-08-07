package groovy

import org.jiakesiws.minipika.components.mql.MqlCallback
import org.junit.Test

class ForGroovy {

  @Test
  void test() {
    User user = new User()
    user.name = "123"
    MqlCallback m = new MqlCallback(MqlMapper)
    println m.invoke("findUser", user, "XXX")
    println m.invoke("addUser", user)
  }

  @Test
  void test2() {
    MqlCallback m = new MqlCallback(MqlMapper)
    List<User> users = [new User("name1"), new User("name2")]
    println m.invoke("addBatch", users)
  }

  @Test
  void test3() {
    MqlCallback m = new MqlCallback(MqlMapper)
    MqlMapper mapper = m.bind()
    List<User> users = [new User("name1"), new User("name2")]
    println mapper.addBatch(users)
  }

}

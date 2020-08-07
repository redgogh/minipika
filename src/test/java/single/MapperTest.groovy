package single

import entity.User
import mapper.UserMapper
import org.jiakesiws.minipika.framework.factory.Factorys
import org.junit.Test

class MapperTest {

  UserMapper mapper = UserMapper.mapper;

  @Test
  void findUserTest() {
    def start = System.currentTimeMillis();
    println mapper.findUser(new User("name3", "pass3"))
    def end = System.currentTimeMillis();
    println end - start + "ms"
  }

  @Test
  void addUserTest() {
    User user = new User("name1", "pas1")
    user.setUsername("key1")
    user.setPassword("value2")
    println mapper.addUser(new User("name2", "pass2"))
  }

  @Test
  void updateUserTest() {
    println mapper.updateUser(1, new User("update1", "update1"))
    println mapper.updateUser(1, new User("update1", "update1"))
    println mapper.updateUser(1, new User("update1", "update1"))
    println mapper.updateUser(1, new User("update1", "update1"))
    println mapper.updateUser(1, new User("update1", "update1"))
    println mapper.updateUser(1, new User("update1", "update1"))
    println mapper.updateUser(1, new User("update1", "update1"))
    println mapper.updateUser(1, new User("update1", "update1"))
  }

  @Test
  void addBatchTest() {
    List<User> users = [new User("name3", "pass3"),
                        new User("name3", "pass3")]
    println mapper.addBatch(users)
  }

}

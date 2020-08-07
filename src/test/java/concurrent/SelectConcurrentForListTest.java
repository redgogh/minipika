package concurrent;

/*
 * Creates on 2019/11/13.
 */

import entity.User;
import mapper.UserMapper;
import org.jiakesiws.minipika.framework.thread.ThreadFactory;
import org.jiakesiws.minipika.framework.thread.Threads;

import java.util.List;

/**
 * @author lts
 * @email jiakesiws@gmail.com
 */
public class SelectConcurrentForListTest {

  static final UserMapper mapper = UserMapper.mapper;

  public static void main(String[] args) {

    ThreadFactory.createThread("thread1", () -> {
      while (true) {
        List<User> user = mapper.findUserList("name1");
        if (user.isEmpty()) continue;
        System.out.println(Threads.getCurrentThreadName() + ": =========>>> " + user.get(0).getId());
      }
    }).start();

    ThreadFactory.createThread("thread2", () -> {
      while (true) {
        List<User> user = mapper.findUserList("name2");
        if (user.isEmpty()) continue;
        System.out.println(Threads.getCurrentThreadName() + ": =========>>> " + user.get(0).getId());
      }
    }).start();

    ThreadFactory.createThread("thread3", () -> {
      int i=0;
      while (true) {
        List<User> user = mapper.findUserList("name3");
        if (user.isEmpty()) continue;
        System.out.println(Threads.getCurrentThreadName() + ": =========>>> " + user.get(0).getId());
        i++;
        if(i == 100) {
          mapper.update("key1", "key1");
        }
      }
    }).start();

  }

}

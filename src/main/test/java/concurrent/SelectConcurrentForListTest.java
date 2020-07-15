package concurrent;

/*
 * Creates on 2019/11/13.
 */

import kt.User;
import mapper.UserMapper;
import org.jiakesimk.minipika.framework.thread.ThreadFactory;
import org.jiakesimk.minipika.framework.thread.Threads;

import java.util.List;

/**
 * @author lts
 */
public class SelectConcurrentForListTest {

  static final UserMapper mapper = UserMapper.Companion.getMapper();

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
      while (true) {
        List<User> user = mapper.findUserList("name3");
        if (user.isEmpty()) continue;
        System.out.println(Threads.getCurrentThreadName() + ": =========>>> " + user.get(0).getId());
      }
    }).start();

  }

}

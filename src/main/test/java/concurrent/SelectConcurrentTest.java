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
public class SelectConcurrentTest {

  static final UserMapper mapper = UserMapper.Companion.getMapper();

  public static void main(String[] args) {

    ThreadFactory.createThreadAndStart("thread1", () -> {
      while (true) {
        List<User> user = mapper.findUser("name1");
        System.out.println(Threads.getCurrentThreadName() + ": =========>>> " + user.get(0).getId());
      }
    });

    ThreadFactory.createThreadAndStart("thread2", () -> {
      while (true) {
        List<User> user = mapper.findUser("name2");
        System.out.println(Threads.getCurrentThreadName() + ": =========>>> " + user.get(0).getId());
      }
    });

    ThreadFactory.createThreadAndStart("thread3", () -> {
      while (true) {
        List<User> user = mapper.findUser("name3");
        System.out.println(Threads.getCurrentThreadName() + ": =========>>> " + user.get(0).getId());
      }
    });

    while (true) {
      List<User> user = mapper.findUser("name4");
      System.out.println(Threads.getCurrentThreadName() + ": =========>>> " + user.get(0).getId());
    }

  }

}

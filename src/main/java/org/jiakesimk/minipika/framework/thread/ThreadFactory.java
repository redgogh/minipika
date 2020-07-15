package org.jiakesimk.minipika.framework.thread;

/*
 * Creates on 2020/5/11.
 */

/**
 * @author lts
 */
public class ThreadFactory {

  /**
   * 创建一个Thread对象，看似一个非常简单的功能但实际上在项目使用上了线程池后需要管理大量的线程，
   * 如果这时候某个线程出现了死锁/阻塞等问题在每个线程都是以Thread-x来命名的话那么将会非常难定位到底是哪个
   * 线程出现了阻塞以及死锁。
   * <p>
   * 所以创建线程的时候统一使用这个方法来创建线程，线程的命名一定是要有意义的命名。
   */
  public static Thread createThread(String name, Runnable runnable) {
    return new Thread(runnable, "project-" + name);
  }

  /**
   * 创建线程并启动
   */
  public static Thread createThreadAndStart(String name, Runnable runnable) {
    Thread thread = createThread(name, runnable);
    thread.start();
    return thread;
  }

}
package org.jiakesimk.minipika.framework.thread;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/*
 * Creates on 2020/5/11.
 */

/**
 * @author lts
 * @email jiakesiws@gmail.com
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
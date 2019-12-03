package com.tractor.framework.thread;

import java.util.concurrent.ExecutorService;

/**
 * Create by 2BKeyboard on 2019/12/3 15:15
 */
public class ThreadPool {

    private ExecutorService pool = null;

    public ThreadPool() {
        this(30);
    }

    public ThreadPool(int size) {

    }

}

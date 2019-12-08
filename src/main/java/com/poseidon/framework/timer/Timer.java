package com.poseidon.framework.timer;

/**
 * 定时器
 * Create by 2BKeyboard on 2019/12/6 21:27
 */
public interface Timer {

    /**
     * 定时器任务
     */
    void run();

    /**
     * 多少时间执行一次
     * @return
     */
    long time();

    /**
     * 立刻停止当前任务，程序不会往下走
     */
    default void stop(){
        TimerManager.getManager().stop(this);
    }

    /**
     * 中断，程序会把这次任务走完再停止
     */
    default void interrupt(){
        TimerManager.getManager().interrupt(this);
    }

    /**
     * 捕获异常
     * @param e
     * @return
     */
    default Object capture(Throwable e){
        e.printStackTrace();
        return null;
    }

}

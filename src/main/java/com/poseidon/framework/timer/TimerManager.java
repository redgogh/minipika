package com.poseidon.framework.timer;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/6 21:28
 */
public class TimerManager {

    private static List<Execute> timerExecutes = new LinkedList<>();

    public static void submit(Timer timer) {
        Execute execute = new Execute(timer);
        execute.start();
        timerExecutes.add(execute);
    }

    /**
     * 立刻停止当前任务
     */
    public static void stop(Timer timer) {
        Execute execute = getExecute(timer);
        execute.stop();
    }

    /**
     * 中断当前程序，程序会走完然后停止
     * @param timer
     */
    public static void interrupt(Timer timer){
        Execute execute = getExecute(timer);
        timerExecutes.remove(execute);
        execute.setInterrupt(true);
    }

    private static Execute getExecute(Timer timer){
        Iterator<Execute> iterator = timerExecutes.iterator();
        while (iterator.hasNext()) {
            Execute execute = iterator.next();
            Timer eTimer = execute.getTimer();
            if (eTimer.equals(timer)) {
                return execute;
            }
        }
        return null;
    }

}

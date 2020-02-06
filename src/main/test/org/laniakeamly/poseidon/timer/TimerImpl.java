package org.laniakeamly.poseidon.timer;

import org.laniakeamly.poseidon.framework.timer.Timer;

/**
 * Copyright: Create by TianSheng on 2019/12/6 21:53
 */
public class TimerImpl implements Timer {

    private int count;

    @Override
    public void run() {
        if (count == 3) {
            interrupt();
            System.out.println("中断处理程序");

        }
        count++;
        System.out.println("timer execute...");
    }

    @Override
    public long time() {
        return 1000;
    }

}

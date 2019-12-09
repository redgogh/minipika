package com.poseidon.model.timer;

import com.poseidon.framework.timer.Timer;
import com.poseidon.framework.tools.TimeUtils;

/**
 * Create by 2BKeyboard on 2019/12/9 11:18
 */
public class Timer1 implements Timer {

    @Override
    public void run() {

        System.out.println("timer1");

    }

    @Override
    public long time() {
        return TimeUtils.SECOND * 5;
    }
}

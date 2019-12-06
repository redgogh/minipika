package com.poseidon.model.timer;

import com.poseidon.framework.timer.Timer;
import com.poseidon.framework.timer.TimerManager;

/**
 * Create by 2BKeyboard on 2019/12/6 22:21
 */
public class TimerExample {

    public static void main(String[] args) {
        Timer timer = new TimerImpl();
        TimerManager.submit(timer);
    }

}

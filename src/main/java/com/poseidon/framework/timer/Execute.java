package com.poseidon.framework.timer;

import lombok.Getter;
import lombok.Setter;

/**
 * Create by 2BKeyboard on 2019/12/6 22:42
 */
public class Execute extends Thread {

    @Getter
    private Timer timer;

    @Setter
    @Getter
    private boolean interrupt = false;

    public Execute(Timer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        try {
            long time = timer.time();
            while (true) {
                timer.run();
                if (!interrupt) {
                    Thread.sleep(time);
                } else {
                    stop();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

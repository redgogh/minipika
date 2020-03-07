package org.poseidon.timer;

import org.laniakeamly.poseidon.framework.timer.Timer;
import org.laniakeamly.poseidon.framework.tools.DateUtils;

/**
 * Copyright: Create by TianSheng on 2019/12/9 11:18
 */
public class Timer1 implements Timer {

    @Override
    public void run() {

        System.out.println("timer1");

    }

    @Override
    public long time() {
        return DateUtils.SECOND * 5;
    }
}

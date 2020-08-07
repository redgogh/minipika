package org.jiakesimk.minipika.framework.timer;

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
 * Creates on 2019/12/6.
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings("deprecation")
public class TimerManager {

    private List<Execute> timerExecutes = new LinkedList<>();

    private static TimerManager manager;

    public static TimerManager getManager() {
        if(manager == null){
            manager = new TimerManager();
        }
        return manager;
    }

    public void submit(Timer timer) {
        Execute execute = new Execute(timer);
        execute.start();
        timerExecutes.add(execute);
    }

    /**
     * 立刻停止当前任务
     */
    public void stop(Timer timer) {
        Execute execute = getExecute(timer);
        execute.stop();
    }

    /**
     * 中断当前程序，程序会走完然后停止
     * @param timer
     */
    public void interrupt(Timer timer){
        Execute execute = getExecute(timer);
        timerExecutes.remove(execute);
        execute.setInterrupt(true);
    }

    private Execute getExecute(Timer timer){
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

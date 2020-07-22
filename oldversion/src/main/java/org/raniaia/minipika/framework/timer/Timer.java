package org.jiakesimk.minipika.framework.timer;

/* ************************************************************************
 *
 * Copyright (C) 2020 tiansheng All rights reserved.
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

/**
 * 定时器
 * @author tiansheng
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
        TimerManager.getManager().stop(this);
        return null;
    }

}

package org.approve.timer;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 */



import org.raniaia.approve.framework.timer.Timer;
import org.raniaia.approve.framework.timer.TimerManager;

/**
 * Copyright: Create by tiansheng on 2019/12/6 22:21
 */
public class TimerExample {

    public static void main(String[] args) {
        TimerManager manager = TimerManager.getManager();
        Timer timer1 = new Timer1();
        Timer timer2 = new Timer2();
        manager.submit(timer1);
        manager.submit(timer2);
    }

}

package org.jiakesimk.minipika.framework.timer;

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

/*
 * Creates on 2019/12/6.
 */

import lombok.Getter;
import lombok.Setter;

/**
 * @author tiansheng
 */
@SuppressWarnings("deprecation")
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
            try {
                while (true) {
                    timer.run();
                    if (!interrupt) {
                        Thread.sleep(time);
                    } else {
                        stop();
                    }
                }
            } catch (Throwable e) {
                timer.capture(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

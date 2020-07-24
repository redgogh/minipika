package org.jiakesimk.minipika.timer;

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



import org.jiakesimk.minipika.framework.timer.Timer;
import org.jiakesimk.minipika.framework.tools.DateUtils;

import java.util.ArrayList;

/**
 * Copyright: Create by tiansheng on 2019/12/9 11:19
 */
public class Timer2 implements Timer {

    private int count;

    @Override
    public void run() {
        if(count == 2){
            ArrayList a = null;
            a.clear();
        }
        System.err.println("timer2");
        count++;
    }

    @Override
    public long time() {
        return DateUtils.SECOND * 2;
    }
}

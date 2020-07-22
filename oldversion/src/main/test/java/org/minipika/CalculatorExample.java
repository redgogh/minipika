package org.minipika;

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



import org.jiakesimk.minipika.framework.tools.Calculator;

/**
 * Copyright: Create by tiansheng on 2019/12/7 21:35
 */
public class CalculatorExample {

    public static void main(String[] args) {
        System.out.println("Java Calc: " + (2000 + 566600 + 7000 * 99 / 20  + 10 / 25 * 60 *20 -10));
        Calculator calculator = new Calculator();
        long sum = calculator.express("2000 + 566600 + 7000 * 99 / 20  + 10 / 25 * 60 *20 -10");
        System.out.println("My Calc: " + sum);
    }

}

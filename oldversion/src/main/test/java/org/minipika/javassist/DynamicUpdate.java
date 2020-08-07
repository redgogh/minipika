package org.jiakesiws.minipika.javassist;

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



import javassist.*;

import java.lang.reflect.Method;

/**
 * Copyright: Create by 2B键盘 on 2019/12/19 10:24
 */
public class DynamicUpdate {

    public static void main(String[] args) throws Exception {

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("org.raniaia.minipika.javassist.DynamicUpdate");
        CtMethod newMethod = CtNewMethod.make("public static void fuck(){ System.out.println(\"hello world by dynamic create!\"); }", ctClass);
        ctClass.addMethod(newMethod);

        Class<?> target = DynamicConstVariable.A_UPDATE;
        Object obj = target.newInstance();

        Method method = target.getDeclaredMethod("fuck");
        method.invoke(obj);

        System.out.println();

    }

}

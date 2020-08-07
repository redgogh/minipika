package org.jiakesiws.minipika;

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




import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 *
 * 获取方法参数
 *
 * Copyright by 2B键盘 on 2020/2/29 23:48
 * @author 2B键盘
 * @version 1.0.0
 * @since 1.8
 */
public class MethodParameter {

    public static PrintStream out = System.out;

    public static String[] displayParametersMetadata(Class<?> clazz,
                                                     String methodName,
                                                     Class<?>... parameterTypes) {
        if (clazz == null) return null;
        try {
            Method method = null;
            if(parameterTypes.length > 0){
                method = clazz.getMethod(methodName,parameterTypes);
            }else {
                Method[] methodArray = clazz.getMethods();
                for (Method method1 : methodArray) {
                    if(methodName.equals(method1.getName())){
                        method = method1;
                        break;
                    }
                }
            }
            for (final Parameter parameter : method.getParameters()) {
                System.out.println(parameter.getName());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(final String[] arguments) {
        displayParametersMetadata(InterfaceDemo.class, "findUserNameById",int.class);
    }
}
package org.poseidon;

import sun.plugin2.util.ParameterNames;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 *
 * 获取方法参数
 *
 * Copyright by tiansheng on 2020/2/29 23:48
 * @author tiansheng
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
package org.poseidon.javassist;

import javassist.*;

import java.lang.reflect.Method;

/**
 * Copyright: Create by TianSheng on 2019/12/19 10:24
 */
public class DynamicUpdate {

    public static void main(String[] args) throws Exception {

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get("org.raniaia.poseidon.javassist.DynamicUpdate");
        CtMethod newMethod = CtNewMethod.make("public static void fuck(){ System.out.println(\"hello world by dynamic create!\"); }", ctClass);
        ctClass.addMethod(newMethod);

        Class<?> target = DynamicUpdate.class;
        Object obj = target.newInstance();

        Method method = target.getDeclaredMethod("fuck");
        method.invoke(obj);

        System.out.println();

    }

}

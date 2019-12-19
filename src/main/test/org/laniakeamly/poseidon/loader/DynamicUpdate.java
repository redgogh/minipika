package org.laniakeamly.poseidon.loader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.laniakeamly.poseidon.framework.loader.PoseidonClassLoader;

import java.lang.reflect.Method;

/**
 * Create by 2BKeyboard on 2019/12/19 10:24
 */
public class DynamicUpdate {

    public static void main(String[] args) throws Exception {
        DynamicUpdate du = new DynamicUpdate();
        String fullClassName = "org.laniakeamly.poseidon.javassist.DynamicUpdate";
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(fullClassName);
        CtMethod newMethod = CtNewMethod.make("public static void fuck(){ System.out.println(\"hello world by dynamic create!\"); }", ctClass);
        ctClass.addMethod(newMethod);

        PoseidonClassLoader classLoader = PoseidonClassLoader.getClassLoader();
        Class<?> target = classLoader.findClassByBytes(fullClassName,ctClass.toBytecode());
        Object copy = classLoader.getObject(target,du);

        copy.getClass().getDeclaredMethod("fuck").invoke(copy);

    }

}

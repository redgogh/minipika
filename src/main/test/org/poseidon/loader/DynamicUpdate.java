package org.poseidon.loader;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.raniaia.poseidon.framework.loader.PoseidonClassLoader;

/**
 * Copyright: Create by tiansheng on 2019/12/19 10:24
 */
public class DynamicUpdate {

    public static void main(String[] args) throws Exception {

        loader("public static void fuck(){ System.out.println(\"hello world by dynamic2 create!\"); }","fuck");
        loader("public static void fuck1(){ System.out.println(\"hello world by dynamic1 create!\"); }","fuck1");

    }

    public static void loader(String method,String name) throws Exception {
        DynamicUpdate du = new DynamicUpdate();
        String fullClassName = "org.raniaia.poseidon.javassist.DynamicUpdate";
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.get(fullClassName);
        ctClass.defrost();
        CtMethod newMethod = CtNewMethod.make(method, ctClass);
        ctClass.addMethod(newMethod);
        PoseidonClassLoader classLoader = new PoseidonClassLoader();
        // todo exception：Exception in thread "main" java.lang.LinkageError: loader (instance of  org/raniaia/poseidon/framework/loader/PoseidonClassLoader): attempted  duplicate class definition for name: "org/raniaia/poseidon/javassist/DynamicUpdate"
        // todo 解决方案：https://blog.csdn.net/is_zhoufeng/article/details/26602689
        Class<?> target = classLoader.findClassByBytes(fullClassName,ctClass.toBytecode());
        Object copy = classLoader.getObject(target,du);

        copy.getClass().getDeclaredMethod(name).invoke(copy);
    }

}

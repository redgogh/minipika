package org.poseidon.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.junit.Test;
import org.laniakeamly.poseidon.framework.beans.PoseidonBeansManager;
import org.laniakeamly.poseidon.framework.loader.PoseidonClassPool;

import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * Copyright by TianSheng on 2020/2/29 2:19
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class MyClassPool {

    @Test
    public void test0() {
        PoseidonClassPool pool = PoseidonBeansManager.getBean("classPool");
        String[] names =
                pool.getMethodParams("org.poseidon.javassist.TestDemo","demo");
        for (String name : names) {
            System.out.println(name);
        }
    }

}

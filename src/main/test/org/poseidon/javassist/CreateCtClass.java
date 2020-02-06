package org.poseidon.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import org.junit.Test;

/**
 * Copyright: Create by TianSheng on 2019/12/19 16:07
 */
public class CreateCtClass {

    @Test
    public void create() throws CannotCompileException {

        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass("a.b.c.d.Ying");
        Class<?> target = ctClass.toClass();
    }

}

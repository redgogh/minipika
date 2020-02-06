package org.poseidon.model;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import org.junit.Test;

/**
 * Create by TianSheng on 2020/2/7 1:56
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class LoaderModel {

    @Test
    public void modifyMethod() throws NotFoundException {
        ClassPool classPool = new ClassPool();
        CtClass ctClass = classPool.getCtClass("org.poseidon.model.LoaderModel");
        System.out.println();
    }

}

package org.poseidon.javassist;

import javassist.CannotCompileException;
import javassist.CtClass;
import org.junit.Test;
import org.raniaia.poseidon.framework.loader.PoseidonClassPool;

/**
 * Copyright: Create by TianSheng on 2019/12/19 16:07
 */
public class CreateCtClass {

    @Test
    public void create() throws CannotCompileException {
        PoseidonClassPool pool = new PoseidonClassPool(true);
        CtClass[] ctClasses = pool.getCtClassArray("org.poseidon.experiment");
        System.out.println();
    }

}

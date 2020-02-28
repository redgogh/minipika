package org.poseidon.javassist;

import org.junit.Test;
import org.laniakeamly.poseidon.framework.beans.PoseidonBeansManager;
import org.laniakeamly.poseidon.framework.loader.PoseidonClassPool;

import java.util.Map;

/**
 * Copyright by TianSheng on 2020/2/29 2:19
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class MyClassPool {

    @Test
    public void test0(){
        PoseidonClassPool pool = PoseidonBeansManager.getBean("classPool ");
        Map<String, Object> getMethodParametersName =
                pool.getMethodParametersName(PoseidonClassPool.class, "getMethodParametersName");
        System.out.println();
    }

}

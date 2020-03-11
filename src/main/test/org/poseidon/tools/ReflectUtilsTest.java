package org.poseidon.tools;

import lombok.Setter;
import org.junit.Test;
import org.raniaia.poseidon.framework.tools.ReflectUtils;

/**
 * Copyright: Create by TianSheng on 2019/12/20 11:55
 */
public class ReflectUtilsTest {

    @Setter
    private String name;

    @Test
    public void test1(){
        ReflectUtilsTest ru = new ReflectUtilsTest();
        ru.setName("zs");
        System.out.println(ReflectUtils.getMemberValue(ru,"name"));
    }

}

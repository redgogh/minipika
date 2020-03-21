package org.regex;

import org.junit.Test;
import org.raniaia.poseidon.framework.tools.PIOUtils;

import java.util.Arrays;

/**
 * Copyright by tiansheng on 2020/2/14 23:57
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class Test1 {

    @Test
    public void test(){

        String input = "a:{aaa:{}} b{}";
        System.out.println(Arrays.asList(PIOUtils.getResourceAsString("/poseidon.jap").split("[\\s\\S]:\\{}",1)));

    }

}

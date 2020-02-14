package org.regex;

import org.junit.Test;
import org.laniakeamly.poseidon.framework.tools.PIOUtils;
import sun.misc.IOUtils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Copyright by TianSheng on 2020/2/14 23:57
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class Test1 {

    @Test
    public void test(){

        String input = "a:{aaa:{}} b{}";
        System.out.println(Arrays.asList(PIOUtils.getResourceAsString("poseidon.jap").split("[\\s\\S]:\\{}",1)));

    }

}

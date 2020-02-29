package org.poseidon;

import org.junit.Test;
import org.laniakeamly.poseidon.framework.annotation.mapper.Query;
import org.laniakeamly.poseidon.framework.mapper.MapperInvocation;

import java.math.BigDecimal;

/**
 * Copyright by TianSheng on 2020/2/29 1:43
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class InvocationTest {

    @Test
    public void mapperInvocationTest0(){
        UserMapper mapper = (UserMapper) MapperInvocation.invoker(UserMapper.class);
        System.out.println(mapper.findProductName(1));
    }

    interface UserMapper{

        @Query
        BigDecimal findProductName(Integer id);

    }

}

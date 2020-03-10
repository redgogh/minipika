package org.poseidon.mapper;

import org.junit.Test;
import org.laniakeamly.poseidon.framework.annotation.mapper.Query;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.beans.PoseidonApplication;
import org.poseidon.experiment.UserInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright by TianSheng on 2020/2/29 1:43
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class InvocationTest {

    @Test
    public void mapperInvocationTest0(){
        UserMapper mapper = PoseidonApplication.getMapper(UserMapper.class);
        // UserMapper mapper = (UserMapper) MapperInvocation.invoker(UserMapper.class);
        // System.out.println(mapper.findUsernameById(4001));
        // System.out.println(mapper.updateUsernameById(4001,"testX"));
        List<UserInfo> list = new ArrayList<>();
        list.add(new UserInfo("l-test-01","xxxx"));
        list.add(new UserInfo("l-test-02","xxxx"));
        list.add(new UserInfo("l-test-03","xxxx"));
        // mapper.addUsers(list);
        mapper.updateUsernameById(4032,"test00001");
    }

    interface UserMapper{

        String findUsernameById(Integer id);

        void updateUsernameById(Integer id,String name);

        void addUsers(List<UserInfo> users);

    }

}

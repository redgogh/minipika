package org.poseidon.mapper;

import org.junit.Test;
import org.raniaia.poseidon.framework.provide.mapper.Query;
import org.raniaia.poseidon.framework.beans.ContextApplication;
import org.poseidon.experiment.UserInfo;

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
        UserMapper mapper = ContextApplication.getMapper(UserMapper.class);
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

        @Query("select * from kkb_user_info where id = ?")
        UserInfo findUserInfoById(Integer id);

    }

    @Test
    public void test02(){
    }

}

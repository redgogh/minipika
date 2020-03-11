package org.keyboard;

import org.junit.Test;
import org.raniaia.poseidon.framework.beans.ContextApplication;
import org.raniaia.poseidon.framework.db.JdbcSupport;
import org.poseidon.experiment.UserInfo;

/**
 * Copyright by TianSheng on 2020/2/8 21:22
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class SaveUserModel {

    private JdbcSupport jdbcSupport = ContextApplication.getBean("jdbc");

    @Test
    public void test(){
        UserInfo info = new UserInfo();
        info.setName("test01");
        info.setEmail("raniaia@123.com");
        System.out.println(jdbcSupport.insert(info));
    }

}

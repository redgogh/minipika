package com.tractor.model;

import com.tractor.framework.db.JdbcSupport;
import com.tractor.framework.db.UnsafeJdbc;
import com.tractor.framework.tools.StringUtils;
import com.tractor.model.experiment.ProductModel;
import com.tractor.model.experiment.UserModel;

import java.util.Date;
import java.util.UUID;

/**
 * 多线程调度测试
 *
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/15 14:15
 * @since 1.8
 */
public class ThreadExample {

    public static int closeCount = 0;
    public static int createCount = 0;

    public static void main(String[] args) {
        userModelInsert();
        // productModelInsert();
    }

    public static void userModelInsert() {
        new UnsafeJdbc().clear(UserModel.class);
        for (int i = 0; i < 150; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    UserModel model = new UserModel();
                    String uuid = UUID.randomUUID().toString();
                    model.setUserName(uuid);
                    model.setGoogleEmail(uuid);
                    model.setAddress(uuid);
                    model.setUuid(uuid);
                    model.setCreateTime(new Date());
                    JdbcSupport.getTemplate().insert(model);
                }
                closeCount++;
                System.err.println(StringUtils.format("线程退出[{}]",closeCount));
            }).start();
            createCount++;
            System.err.println(StringUtils.format("线程创建[{}]",createCount));
        }
    }

    public static void productModelInsert() {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 20; j++) {
                    ProductModel model = new ProductModel();
                    String uuid = UUID.randomUUID().toString();
                    model.setProductName("产品[".concat(String.valueOf(new Date().getTime())).concat("]"));
                    model.setUuid(uuid);
                    JdbcSupport.getTemplate().insert(model);
                }
                System.err.println("线程退出");
            }).start();
            System.err.println("线程创建");
        }
    }

}

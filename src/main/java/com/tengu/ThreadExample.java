package com.tengu;

import com.tengu.db.JdbcFunction;
import com.tengu.experiment.UserModel;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.UUID;

/**
 * 多线程调度测试
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/15 14:15
 * @since 1.8
 */
public class ThreadExample {

    public static void main(String[] args) {

        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);

        for (int i = 0; i < 2000; i++) {
            new Thread(() -> {
                for(int j=0; j<100; j++){
                    UserModel model = new UserModel();
                    String uuid = UUID.randomUUID().toString();
                    model.setUserName(uuid);
                    model.setUuid(uuid);
                    model.setCreateTime(new Date());
                    JdbcFunction.getFunction().insert(model);
                }
                System.err.println("线程退出");
            }).start();
            System.err.println("线程创建");
        }

    }


}

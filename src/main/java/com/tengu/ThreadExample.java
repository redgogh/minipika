package com.tengu;

import com.tengu.db.JdbcFunction;
import com.tengu.experiment.UserModel;

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

        for (int i = 0; i < 2000; i++) {
            new Thread(() -> {
                for(int j=0; j<1000; j++){
                    UserModel model = new UserModel();
                    String uuid = UUID.randomUUID().toString();
                    model.setUserName(uuid);
                    model.setUuid(uuid);
                    model.setCreateTime(new Date());
                    JdbcFunction.getFunction().insert(model);
                }
                System.err.println("线程退出");
            }).start();
        }

    }


}

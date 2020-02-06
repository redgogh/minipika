package org.laniakeamly.poseidon;

import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.cache.PoseidonCache;
import org.laniakeamly.poseidon.framework.db.JdbcSupport;
import org.laniakeamly.poseidon.framework.db.NativeResult;
import org.laniakeamly.poseidon.framework.tools.StringUtils;
import org.laniakeamly.poseidon.experiment.ProductModel;
import org.laniakeamly.poseidon.experiment.UserModel;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 多线程调度测试
 *
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/15 14:15
 * @since 1.8
 */
public class ThreadExample {

    public static int closeCount = 0;
    public static int createCount = 0;

    public static final JdbcSupport jdbc = BeansManager.getBean("jdbc");

    public static void main(String[] args) {
        userModelInsert();
        // productModelInsert();
        // cacheReadAndWrite();
    }

    public static void userModelInsert() {
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    UserModel model = new UserModel();
                    String uuid = UUID.randomUUID().toString();
                    model.setUserName(uuid);
                    model.setGoogleEmail(uuid);
                    model.setAddress(uuid);
                    model.setUuid(uuid);
                    model.setCreateTime(new Date());
                    jdbc.insert(model);
                }
                closeCount++;
                System.err.println(StringUtils.format("线程退出[{}]", closeCount));
            }).start();
            createCount++;
            System.err.println(StringUtils.format("线程创建[{}]", createCount));
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
                    jdbc.insert(model);
                }
                System.err.println("线程退出");
            }).start();
            System.err.println("线程创建");
        }
    }

    public static void cacheReadAndWrite() {
        final PoseidonCache cache = BeansManager.getBean("cache");
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 200; j++) {
                    if(j%2==0) {
                        cache.save("select", new NativeResult() {
                            @Override
                            public NativeResult build(ResultSet rset) {
                                return null;
                            }

                            @Override
                            public <T> T conversionJavaBean(Class<T> target) {
                                return null;
                            }

                            @Override
                            public <T> List<T> conversionJavaList(Class<T> target) {
                                return null;
                            }

                            @Override
                            public String toJSONString() {
                                return null;
                            }

                            @Override
                            public void hasNext() {

                            }

                            @Override
                            public String next() {
                                return null;
                            }

                            @Override
                            public void reset() {

                            }
                        }, "a");
                    }else{
                        cache.refreshAll();
                    }
                }
                closeCount++;
                System.err.println("线程退出:["+closeCount+"]");
                System.out.println();
            }).start();
            createCount++;
            System.err.println("线程创建["+createCount+"]");
        }
    }

}

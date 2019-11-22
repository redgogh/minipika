package com.tengu.pool;

import com.tengu.config.Config;
import com.tengu.config.Initialize;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 连接池
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/11 16:31
 * @since 1.8
 */
public class ConnectionPool {

    /**
     * 驱动对象
     */
    private static Driver driver;

    /**
     * 单例模式---实例对象
     */
    private static ConnectionPool instance;

    /**
     * 连接池最小空间
     */
    private static int MIN_SIZE;

    /**
     * 最大空间
     */
    private static int MAX_SIZE;

    /**
     * 链接创建总数
     */
    private static int count = 0;

    private static String jdbcUrl;

    private static Set<Connection> conns = new LinkedHashSet<>();

    // 创建连接
    private static Properties info = new Properties();

    static {
        try {
            // 设置最小值和最大值
            MIN_SIZE = Config.getMinSize();
            MAX_SIZE = Config.getMaxSize();
            // 设置属性
            info.setProperty("user", Config.getUsername());
            info.setProperty("password", Config.getPassword());
            // URL
            jdbcUrl = Config.getUrl();
            // 初始化连接对象
            for (int i = 0; i < MIN_SIZE; i++) {
                conns.add(ConnectionPool.getPool().createConnection());
            }
            // 当连接创建开始初始化
            Initialize init = new Initialize();
            init.parseModel();
            init.columnCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getPool() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * 获取一个连接
     *
     * @return
     */
    public Connection getConnection() {
        synchronized (this) {
            try {
                if (!conns.isEmpty()) {
                    Iterator<Connection> iter = conns.iterator();
                    while (iter.hasNext()) {
                        Connection connection = iter.next();
                        conns.remove(connection);
                        System.out.println(Thread.currentThread().getName() + "：取出一个链接，连接池中剩余链接有" + conns.size() + "个");
                        return connection;
                    }
                    System.err.println(Thread.currentThread().getName() + "：当前连接池中没有链接了，等待中....");
                    wait();
                    System.err.println(Thread.currentThread().getName() + "被唤醒");
                    return getConnection();
                } else {
                    if (count < MAX_SIZE) {
                        final Connection connection = createConnection();
                        if (connection == null) {
                            wait();
                            return getConnection();
                        } else {
                            return connection;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 创建连接
     *
     * @return
     */
    public Connection createConnection() {
        if (count >= MAX_SIZE) {
            return null;
        }
        System.out.println("已创建的链接有：" + count);
        try {
            if (driver == null) {
                DriverLoader driverLoader = new DriverLoader();
                driver = driverLoader.getDriver();
            }
            final Connection connection = driver.connect(jdbcUrl, info);
            count++;
            return connection;
        } catch (Exception e) {
            if (e instanceof SQLNonTransientConnectionException) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 归还链接
     *
     * @param connection
     */
    public void release(Connection connection) {
        synchronized (this) {
            try {
                if (conns.size() >= count) {
                    connection.close();
                    return;
                }
                conns.add(connection);
                notifyAll(); // 唤醒所有线程
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

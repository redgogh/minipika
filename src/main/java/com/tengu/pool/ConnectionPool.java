package com.tengu.pool;

import com.tengu.config.Config;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

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

    private static Set<Connection> conns;

    public ConnectionPool(){
        init();
    }

    // 初始化
    private void init(){
        for (int i = 0; i < MIN_SIZE; i++) {
            conns.add(createConnection());
        }
        this.MIN_SIZE = Config.getMinSize();
        this.MAX_SIZE = Config.getMaxSize();
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
                if (conns == null) conns = new HashSet<>();
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
                    if (count <= MAX_SIZE) {
                        Connection connection = createConnection();
                        if(connection == null){
                            wait();
                            return getConnection();
                        }else{
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
    public static Connection createConnection() {
        if(count >= MAX_SIZE) return null;
        System.out.println("已创建的链接有：" + count);
        try {
            if (driver == null) {
                driver = (Driver) Class.forName(Config.getDriver()).newInstance();
            }
            // 创建连接
            Properties info = new Properties();
            info.setProperty("user", Config.getUsername());
            info.setProperty("password", Config.getPassword());
            final Connection connection = driver.connect(Config.getUrl(), info);
            count++;
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 归还链接
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

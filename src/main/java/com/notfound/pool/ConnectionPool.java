package com.notfound.pool;

import com.notfound.config.Config;

import java.sql.Connection;
import java.sql.Driver;
import java.util.LinkedList;
import java.util.Properties;

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
    private static int MIN_SIZE = Config.getMinSize();

    /**
     * 最大空间
     */
    private static int MAX_SIZE = Config.getMaxSize();

    private static LinkedList<Connection> conns = new LinkedList<>();

    static{
        for(int i=0; i<MIN_SIZE; i++){
            conns.add(createConnection());
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
     * @return
     */
    public Connection getConnection() {
        synchronized(conns) {
            if (!conns.isEmpty()) {
                return conns.removeFirst();
            }
            return null;
        }
    }

    /**
     * 创建连接
     * @return
     */
    private static Connection createConnection() {
        // 连接对象
        Connection connection = null;
        try {
            if (driver == null) {
                driver = (Driver) Class.forName(Config.getDriver()).newInstance();
            }
            // 创建连接
            Properties info = new Properties();
            info.setProperty("user", Config.getUsername());
            info.setProperty("password", Config.getPassword());
            connection = driver.connect(Config.getUrl(), info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void release(Connection connection) {

    }

    public void destroy(Connection connection) {

    }

    public void check() {

    }

}

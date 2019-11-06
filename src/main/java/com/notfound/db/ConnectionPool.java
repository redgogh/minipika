package com.notfound.db;

import com.notfound.config.Config;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

/**
 * 连接池
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:10
 * @since 1.8
 */
public class ConnectionPool {

    /**
     * 驱动对象
     */
    private static Driver driver;

    /**
     * 连接池对象
     */
    private static ConnectionPool pool;

    /**
     * 查询专用连接池
     * LinkedList集合, 储存连接容器 --- 连接池
     */
    private static LinkedList<Connection> connections = new LinkedList<>();

    /**
     * 最大连接
     * 配置最大连接 * 2,一半将给予查询,另一半将给予写入.
     */
    private static int MAX_SIZE = Config.getMaxSize();

    /**
     * 最小连接
     * 配置最小连接 * 2,一半将给予查询,另一半将给予写入.
     */
    private static int MIN_SIZE = Config.getMinSize();

    /**
     * 初始化连接池
     */
    static {
        for (int i = 0; i < MIN_SIZE; i++) {
            connections.add(createConnection());
        }
    }

    public static ConnectionPool getConnectionPool() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    /**
     * 获取连接
     *
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

    /**
     * 获取连接
     *
     * @return
     */
    public synchronized Connection getConnection() {
        if (connections.isEmpty()) {
            connections.add(createConnection());
        }
        return connections.removeFirst();
    }

    /**
     * 归还连接
     *
     * @param connection
     */
    public void release(Connection connection) {
        if (connections.size() < MAX_SIZE) {
            connections.add(connection);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

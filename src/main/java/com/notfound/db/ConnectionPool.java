package com.notfound.db;

import com.notfound.config.Config;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
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
    private static LinkedList<Connection> queryConnections = new LinkedList<>();

    /**
     * 写入专用连接池
     * LinkedList集合, 储存连接容器 --- 连接池
     */
    private static LinkedList<Connection> writeConnections = new LinkedList<>();

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
        // 根据最小连接创建连接池
        int half = MIN_SIZE / 2;
        // 创建查询专用连接
        for (int i = 0; i < half; i++) {
            queryConnections.add(createConnection());
        }
        // 创建写入专用连接
        for (int i = 0; i < half; i++) {
            writeConnections.add(createConnection());
        }
    }

    public static ConnectionPool getConnectionPool(){
        if(pool == null){
            pool = new ConnectionPool();
        }
        return pool;
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection createConnection() {
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
     * 获取查询连接
     *
     * @return
     */
    public Connection getQueryConnection() {
        if (queryConnections.isEmpty()) {
            queryConnections.add(createConnection());
        }
        return queryConnections.removeFirst();
    }

    /**
     * 获取写入连接
     *
     * @return
     */
    public Connection getWriteConnection() {
        if (writeConnections.isEmpty()) {
            writeConnections.add(createConnection());
        }
        return writeConnections.removeFirst();
    }

    /**
     * 归还查询连接
     *
     * @param connection
     */
    public void releaseQuery(Connection connection) {
        if (queryConnections.size() < (MAX_SIZE / 2)) {
            queryConnections.add(connection);
        }
    }

    /**
     * 归还写入连接
     *
     * @param connection
     */
    public void releaseWrite(Connection connection) {
        if (writeConnections.size() < (MAX_SIZE / 2)) {
            writeConnections.add(connection);
        }
    }



}

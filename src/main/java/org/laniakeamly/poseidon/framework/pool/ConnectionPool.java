package org.laniakeamly.poseidon.framework.pool;

import org.laniakeamly.poseidon.framework.config.GlobalConfig;
import org.laniakeamly.poseidon.framework.config.PropertiesConfig;
import org.laniakeamly.poseidon.framework.monitor.MonitorObject;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 连接池
 *
 * Default connection pool.
 *
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/11 16:31
 * @since 1.8
 */
public class ConnectionPool extends MonitorObject
        implements org.laniakeamly.poseidon.extension.ConnectionPool {

    /**
     * 驱动对象
     */
    private static Driver driver;

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

    private static final Boolean transaction = GlobalConfig.getConfig().getTransaction();

    // 创建连接
    private static Properties info = new Properties();

    private static boolean isInit = false;

    public ConnectionPool(){
        if(!isInit) {
            try {
                isInit = true;
                // 设置最小值和最大值
                MIN_SIZE = GlobalConfig.getConfig().getMinSize();
                MAX_SIZE = GlobalConfig.getConfig().getMaxSize();
                // 设置属性
                info.setProperty("user", GlobalConfig.getConfig().getUsername());
                info.setProperty("password", GlobalConfig.getConfig().getPassword());
                // URL
                jdbcUrl = GlobalConfig.getConfig().getUrl();
                // 初始化连接对象
                for (int i = 0; i < MIN_SIZE; i++) {
                    conns.add(createConnection());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取一个连接
     *
     * @return
     */
    public Connection getConnection() {
        synchronized (this) {
            if (!conns.isEmpty()) {
                Iterator<Connection> iter = conns.iterator();
                while (iter.hasNext()) {
                    Connection connection = iter.next();
                    conns.remove(connection);
                    return connection;
                }
                await();
                Connection connection = getConnection();
                return connection;
            } else {
                if (count < MAX_SIZE) {
                    final Connection connection = createConnection();
                    if (connection == null) {
                        await();
                        return getConnection();
                    }
                    return connection;
                } else {
                    await();
                    return getConnection();
                }
            }
        }
    }

    /**
     * 创建连接
     *
     * @return
     */
    public Connection createConnection() {
        if (count >= MAX_SIZE) return null;
        try {
            if (this.driver == null) {
                DriverLoader driverLoader = new DriverLoader();
                this.driver = driverLoader.getDriver();
            }
            final Connection connection = this.driver.connect(jdbcUrl, info);
            if (transaction) connection.setAutoCommit(false);
            count++;
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
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

    public void await() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

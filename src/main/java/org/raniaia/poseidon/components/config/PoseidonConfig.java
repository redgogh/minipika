package org.raniaia.poseidon.components.config;

import org.raniaia.poseidon.framework.exception.runtime.ConfigException;
import org.raniaia.poseidon.framework.tools.StringUtils;

/**
 * Copyright Create by tiansheng on 2020/2/13 2:43
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
interface PoseidonConfig {

    enum DriverType {MYSQL, ORACLE}

    /**
     * 加载驱动
     */
    default DriverType loadDriver(String classpath) {
        if (StringUtils.isEmpty(classpath)) {
            throw new ConfigException("jdbc driver cannot null.");
        }
        System.setProperty("jdbc.drivers", classpath);
        if (classpath.contains("mysql")) {
            return DriverType.MYSQL;
        } else if (classpath.contains("oracle")) {
            return DriverType.ORACLE;
        }
        return DriverType.MYSQL;
    }

    /**
     * get config value
     *
     * @param key
     * @return
     */
    String getValue(String key);

}

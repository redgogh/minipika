package org.raniaia.poseidon.framework.config;

/**
 * Copyright Create by tiansheng on 2020/2/13 2:43
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
interface PoseidonConfig {

    /**
     * 加载驱动
     */
    void loadDriver(String classpath);

    /**
     * get config value
     * @param key
     * @return
     */
    String getValue(String key);

}

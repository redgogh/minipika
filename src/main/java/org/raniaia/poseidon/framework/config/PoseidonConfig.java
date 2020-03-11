package org.raniaia.poseidon.framework.config;

/**
 * Copyright Create by TianSheng on 2020/2/13 2:43
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public interface PoseidonConfig {

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

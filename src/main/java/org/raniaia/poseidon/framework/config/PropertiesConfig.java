package org.raniaia.poseidon.framework.config;

import org.raniaia.poseidon.framework.tools.*;

/**
 * 配置类
 *
 * Config object.
 *
 * Copyright: Create by TianSheng on 2019/11/4 14:10
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 *
 */
public final class PropertiesConfig extends AbstractConfig {

    // 配置文件地址
    private String configPath = "poseidon.properties";

    private static PropertiesConfig instance;

    public static PropertiesConfig getInstance() {
        if (instance == null) {
            instance = new PropertiesConfig("poseidon.properties");
        }
        return instance;
    }

    public PropertiesConfig(String configPath) {
        super(PIOUtils.getResourceAsProperties(configPath));
        instance = this;
    }


}

package org.raniaia.poseidon.components.config.components;

import org.raniaia.poseidon.components.config.AbstractConfig;
import org.raniaia.poseidon.framework.provide.component.Component;
import org.raniaia.poseidon.framework.tools.*;

/**
 * 配置类
 *
 * Config object.
 *
 * Copyright: Create by tiansheng on 2019/11/4 14:10
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 *
 */
public final class PropertiesConfig extends AbstractConfig {

    // 配置文件地址
    private String configPath = "resources/poseidon.properties";

    private static PropertiesConfig instance;

    public static PropertiesConfig getInstance() {
        if (instance == null) {
            instance = new PropertiesConfig("resources/poseidon.properties");
        }
        return instance;
    }

    public PropertiesConfig(String configPath) {
        super(PIOUtils.getResourceAsProperties(configPath));
        instance = this;
    }


}

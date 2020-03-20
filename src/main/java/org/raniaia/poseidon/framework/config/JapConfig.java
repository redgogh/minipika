package org.raniaia.poseidon.framework.config;

import org.raniaia.poseidon.framework.jap.JapLoader;

import java.util.Map;

/**
 * jap config配置文件，全称是Json and properties
 * 这个配置文件由当前框架所创造的。
 *
 * jap config file full name is Json and properties.
 * this config file create by poseidon.
 *
 * Copyright by tiansheng on 2020/2/13 2:40
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class JapConfig extends AbstractConfig {

    private Map<String, String> config = null;

    private static JapConfig japConfig;

    public static JapConfig getJapConfig() {
        if (japConfig == null) {
            japConfig = new JapConfig();
        }
        return japConfig;
    }

    public JapConfig() {
        super();
    }

    public JapConfig(String path) {
        super(new JapLoader().load(path).get("poseidon"));
    }

}

package org.laniakeamly.poseidon.framework.config;

import org.laniakeamly.poseidon.framework.tools.StringUtils;

/**
 * 手动加载配置文件
 *
 * manual loader config file.
 *
 * Copyright: Create by TianSheng on 2019/12/3 22:06
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class ManualConfig {

    private ManualConfig(){}

    public static void load(String configPath){
        if(StringUtils.isEmpty(configPath))
            throw new NullPointerException("config path cannot null");
        new Config(configPath);
    }

}

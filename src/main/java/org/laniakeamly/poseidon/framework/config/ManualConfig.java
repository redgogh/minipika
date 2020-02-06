package org.laniakeamly.poseidon.framework.config;

import org.laniakeamly.poseidon.framework.tools.StringUtils;

/**
 * 加载自定义配置文件
 * Copyright: Create by 2BKeyboard on 2019/12/3 22:06
 */
public class ManualConfig {

    private ManualConfig(){}

    public static void load(String configPath){
        if(StringUtils.isEmpty(configPath))
            throw new NullPointerException("config path cannot null");
        new Config(configPath);
    }

}

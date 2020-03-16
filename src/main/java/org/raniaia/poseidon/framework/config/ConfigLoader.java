package org.raniaia.poseidon.framework.config;

import jdk.nashorn.internal.objects.Global;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.net.URL;

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
public class ConfigLoader {

    private ConfigLoader() {
    }

    public static void loadConfig(String configPath) {
        URL url = Thread.currentThread().getStackTrace()[2].getClass().getResource("");
        if (StringUtils.isEmpty(configPath)) {
            throw new NullPointerException("config path cannot null");
        }
        if (url != null && "jar".equals(url.getProtocol())) {
            GlobalConfig.setJar(true);
        }
        GlobalConfig.loaderConfig(configPath);
    }

}

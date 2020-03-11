package org.raniaia.poseidon.framework.log.slf4j;

import org.raniaia.poseidon.framework.log.LogAdapter;
import org.raniaia.poseidon.framework.log.Log;

/**
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class Slf4jAdapter implements LogAdapter {

    @Override
    public Log getLog(String key) {
        return new Slf4jLog(org.slf4j.LoggerFactory.getLogger(key));
    }

    @Override
    public Log getLog(Class<?> key) {
        return new Slf4jLog(org.slf4j.LoggerFactory.getLogger(key));
    }

}

package org.laniakeamly.poseidon.framework.beans;

import org.laniakeamly.poseidon.extension.Logger;

/**
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class Beans {

    static Logger getLogger(){
        return BeansManager.getBean("logger");
    }

}

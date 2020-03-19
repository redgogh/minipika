package org.raniaia.poseidon.framework.modules.model;

import org.raniaia.poseidon.framework.modules.BaseModuleAdapter;

/**
 * {@code ModelLoader}接口
 *
 * <p>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public interface ModelLoader extends BaseModuleAdapter {

    /**
     * 执行<b>model扫描，并生成表</b>
     */
    void run();

}

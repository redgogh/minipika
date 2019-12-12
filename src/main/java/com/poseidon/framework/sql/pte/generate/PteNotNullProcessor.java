package com.poseidon.framework.sql.pte.generate;

import com.poseidon.framework.tools.PteString;

/**
 * Create by 2BKeyboard on 2019/12/12 18:49
 */
public interface PteNotNullProcessor {

    /**
     * line属性处理
     */
    void line(PteString value);

    /**
     * process属性处理
     */
    void process(PteString value);

}

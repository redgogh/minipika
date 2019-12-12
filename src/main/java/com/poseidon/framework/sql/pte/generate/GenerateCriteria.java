package com.poseidon.framework.sql.pte.generate;

import com.poseidon.framework.tools.PteString;

/**
 * Create by 2BKeyboard on 2019/12/12 14:46
 */
public interface GenerateCriteria {

    /**
     * [@NotNull]
     *
     * @param pteStr 被解析后的字符串
     * @return 生成好的Java代码
     */
    String _NotNull(PteString pteStr);

    /**
     * [#*]
     * 例如：[#username]
     *
     * @param pteStr 被解析后的字符串
     * @return 生成好的Java代码
     */
    String _Include(PteString pteStr);

}

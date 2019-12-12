package com.poseidon.framework.sql.pte.generate;

import com.poseidon.framework.tools.PteString;

/**
 * Create by 2BKeyboard on 2019/12/12 14:46
 */
public interface GenerateCriteria {

    /**
     * [@NotNull]
     *
     * @param str 被解析后的字符串
     * @param line 当前解析的代码所在行数
     *
     * @return 生成好的Java代码
     */
    String _NotNull(String str,int line);

    /**
     * [#*]
     * 例如：[#username]
     *
     * @param str 被解析后的字符串
     * @param line 当前解析的代码所在行数
     *
     * @return 生成好的Java代码
     */
    void _Include(String str,int line);

}

package com.poseidon.framework.sql.pte;

import java.util.Map;

/**
 * 记录标记语言位置
 * Create by 2BKeyboard on 2019/12/12 16:30
 */
public class PteMarkLanguagePosition {

    /**
     * 记录标记语言位置
     * 第一层Map为pte文件中定义的builder
     * 第二层Map存放的是标记位置,key是builder下定义的mapper
     *
     * 假设我们当前有这么一段代码
     * <code>
     *      username = [#username]
     * </code>
     *
     * 那么底层Map的value就是[#username]的开始位置和结束位置
     * 这样做的目的是为了能够进行更高效的替换数据并且不需要消耗过多的性能
     *
     * 这样来看那段代码被标记的位置就是 11,22, 所以第二层Map的Value就是保存的 Integer[]{11,22}
     */
    private Map<String, Map<String, Integer[]>> pos;

}

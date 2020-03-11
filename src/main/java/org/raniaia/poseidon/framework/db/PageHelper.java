package org.raniaia.poseidon.framework.db;

/**
 * 分页插件
 * Copyright: Create by TianSheng on 2019/12/3 11:18
 */
public class PageHelper extends NativePageHelper {

    public PageHelper(Class<?> generic) {
        super(generic);
    }

    public PageHelper(int pageNum, int pageSize, Class<?> generic) {
        super(pageNum, pageSize, generic);
    }

}

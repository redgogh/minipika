package org.laniakea.poseidon.framework.db;

/**
 * 分页插件
 * Create by 2BKeyboard on 2019/12/3 11:18
 */
public class PageHelper extends NativePageHelper {

    public PageHelper(Class<?> generic) {
        super(generic);
    }

    public PageHelper(int pageNum, int pageSize, Class<?> generic) {
        super(pageNum, pageSize, generic);
    }

}

package org.laniakeamly.poseidon.framework.sql.xml;

/**
 * 回调
 * Create by 2BKeyboard on 2019/12/23 11:17
 */
public interface Callback<T> {

    /**
     * 回调执行方法
     * @param t
     */
    void execute(T t);

}

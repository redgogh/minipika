package org.laniakeamly.poseidon.framework.sql;

/**
 * 回调
 * Create by 2BKeyboard on 2019/12/23 11:17
 */
public interface Callback<T> {
    void execute(T t);
}

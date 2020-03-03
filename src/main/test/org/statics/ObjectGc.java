package org.statics;

/**
 * Copyright by TianSheng on 2020/3/3 15:54
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class ObjectGc {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("对象被回收 - [" + this + "]");
    }
}

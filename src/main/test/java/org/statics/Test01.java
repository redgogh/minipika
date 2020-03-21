package org.statics;

import org.junit.Test;

/**
 * Copyright by tiansheng on 2020/3/3 15:53
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class Test01 {

    public static ObjectGc newObject() {
        return new ObjectGc();
    }

    public void create() {
        ObjectGc objectGc = newObject();
        System.out.println("对象创建成功 - " + objectGc);
    }

    @Test
    public void main(){
        create();
        // 执行gc
        System.gc();
        while (true);
    }

}

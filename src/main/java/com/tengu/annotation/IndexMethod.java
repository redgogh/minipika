package com.tengu.annotation;

/**
 * @author 404NotFoundx
 * @date 2019/11/18 15:39
 * @version 1.0.0
 * @since 1.8
 */
public enum IndexMethod {

    /**
     * HASH算法，查找速度最快
     * 但是HASH算法不适用于范围查找，比如 N < 20
     */
    HASH,

    /**
     * B+树，适用于任何方式的查找，但是速度低于HASH
     */
    BTREE

}

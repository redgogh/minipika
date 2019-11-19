package com.tengu.annotation;

/**
 * @author 404NotFoundx
 * @date 2019/11/18 15:39
 * @version 1.0.0
 * @since 1.8
 */
public enum IndexType {

    /**
     * 全文索引
     */
    FULLTEXT,

    /**
     * 普通索引
     */
    NORMAL,

    /**
     * 空间索引
     * 注：innodb储存引擎不支持该索引
     */
    SPATIAL,

    /**
     * 唯一索引
     */
    UNIQUE

}

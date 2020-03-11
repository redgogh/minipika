package org.raniaia.poseidon.framework.model.database;

import lombok.Data;

/**
 * 字段模型
 * Copyright by TianSheng on 2020/2/15 1:45
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Data
public class ColumnModel {

    /**
     * 字段名称
     */
    private String Field;

    /**
     * 字段类型
     */
    private String Type;

    /**
     * Collation代表字符编码。
     * 拥有这个属性的前提是你的字段类型必须是varchar或其他的字符类型。
     */
    private String Collation;

    /**
     * 是否为空
     */
    private String Null;

    /**
     * 键
     */
    private String Key;

    /**
     * 默认数据
     */
    private String Default;

    /**
     * 其他属性，比如auto_increment
     */
    private String Extra;

    /**
     * 权限
     */
    private String Privileges;

    /**
     * 注释
     */
    private String Comment;

}

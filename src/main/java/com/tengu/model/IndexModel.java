package com.tengu.model;

import lombok.Data;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/19 18:13
 * @since 1.8
 */
@Data
public class IndexModel {

    private String Table;
    private String NonUnique;       // 0:唯一 1:不唯一
    private String KeyName;         // 索引别名
    private String SeqInIndex;
    private String ColumnName;      // 索引字段
    private String Collation;
    private String Cardinality;
    private String SubPart;
    private String Packed;
    private String Null;
    private String IndexType;       // 索引类型
    private String Comment;
    private String IndexComment;

    public com.tengu.annotation.IndexType getType(){
        if(IndexType.toUpperCase().equals("BTREE") && NonUnique.equals("0")){
            return com.tengu.annotation.IndexType.UNIQUE;
        }
        if(IndexType.toUpperCase().equals("BTREE") && NonUnique.equals("1")){
            return com.tengu.annotation.IndexType.NORMAL;
        }
        if(IndexType.toUpperCase().equals("FULLTEXT")){
            return com.tengu.annotation.IndexType.FULLTEXT;
        }
        if(IndexType.toUpperCase().equals("SPATIAL")){
            return com.tengu.annotation.IndexType.SPATIAL;
        }
        return null;
    }

}

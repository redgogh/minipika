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
    private String NonUnique;
    private String KeyName;
    private String SeqInIndex;
    private String ColumnName;
    private String Collation;
    private String Cardinality;
    private String SubPart;
    private String Packed;
    private String Null;
    private String IndexType;
    private String Comment;
    private String IndexComment;

}

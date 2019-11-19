package com.tengu.model;

import lombok.Data;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/19 15:59
 * @since 1.8
 */
@Data
public class IndexAttribute {

    private String table;
    private String nonUnique;
    private String keyName;
    private String seqInIndex;
    private String columnName;
    private String collation;
    private String cardinality;
    private String subPart;
    private String packed;
    private String _null;
    private String indexType;
    private String comment;
    private String indexComment;

}

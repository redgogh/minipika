package org.laniakeamly.poseidon.framework.sql;

/**
 * Create by 2BKeyboard on 2019/12/17 18:29
 */
public class ProvideConstant {

    public static final String IF                                       = "if";
    public static final String REQ                                      = "$req";
    public static final Object NULL                                     = "null";
    public static final String TEXT                                     = "text";
    public static final String ELSE                                     = "else";
    public static final String ITEM                                     = "item";
    public static final String INDEX                                    = "index";
    public static final String BATCH                                    = "batch";
    public static final String SELECT                                   = "select";
    public static final String INSERT                                   = "insert";
    public static final String UPDATE                                   = "update";
    public static final String DELETE                                   = "delete";
    public static final String ONENESS                                  = "oneness";
    public static final String CHOOSE                                   = "choose";
    public static final String FOREACH                                  = "foreach";
    public static final String IF_TEST                                  = "test";
    public static final String CONDITION                                = "cond";
    public static final String PARAMETER                                = "parameter";
    public static final String PARAMS_MAP                               = "map";
    public static final String PARAMS_MAP_GET                           = PARAMS_MAP.concat(".get(\"{}\");");
    public static final String PARAMS_LIST                              = "params";
    public static final String $PARAMETER                               = "$parameter";
    public static final String SQL_APPEND                               = "sql.append(\" {} \");";
    public static final String COLLECTIONS                              = "collections";
    public static final String SQL_PARAMS_SET                           = "params";
    public static final String PARAMS_LIST_ADD                          = PARAMS_LIST.concat(".add({});");
    public static final String CLASS_FULL_NAME                          = "org.laniakeamly.poseidon.$builder.";
    public static final String GET_MEMBER_VALUE                         = "org.laniakeamly.poseidon.framework.tools.ReflectUtils.getMemberValue({},{})";
    public static final String PARAMETER_SELECT                         = "this:";
    public static final String INSERT_FOREACH_TAG                       = "[infor]:";
    public static final String ONENESS_ATTRIBUTE_KEY                    = "id";
    public static final String PARAMETER_OBJECT_LOCATION                = "org.laniakeamly.poseidon.framework.loader.Parameter";


}

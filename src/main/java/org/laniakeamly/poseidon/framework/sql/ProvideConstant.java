package org.laniakeamly.poseidon.framework.sql;

import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by 2BKeyboard on 2019/12/17 18:29
 */
public class ProvideConstant {

    public static final String IF = "if";
    public static final String REQ = "$req";
    public static final Object NULL = "null";
    public static final String TEXT = "text";
    public static final String ELSE = "else";
    public static final String ITEM = "item";
    public static final String INDEX = "index";
    public static final String BATCH = "batch";
    public static final String SELECT = "select";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String ONENESS = "oneness";
    public static final String CHOOSE = "choose";
    public static final String FOREACH = "foreach";
    public static final String IF_TEST = "test";
    public static final String CONDITION = "cond";
    public static final String PARAMETER = "parameter";
    public static final String PARAMS_MAP = "map";
    public static final String PARAMS_MAP_GET = PARAMS_MAP.concat(".get(\"{}\");");
    public static final String PARAMS_LIST = "params";
    public static final String $PARAMETER = "$parameter";
    public static final String COLLECTIONS = "collections";
    public static final String SQL_PARAMS_SET = "params";
    public static final String PARAMS_LIST_ADD = PARAMS_LIST.concat(".add({});");
    public static final String CLASS_FULL_NAME = "org.laniakeamly.poseidon.$builder.";
    public static final String PARAMETER_SELECT = "this:";
    public static final String INSERT_FOREACH_TAG = "[infor]:";
    public static final String ONENESS_ATTRIBUTE_KEY = "id";
    public static final String PARAMETER_OBJECT_LOCATION = "org.laniakeamly.poseidon.framework.loader.Parameter";

    // ---------------------------------------------------------------------

    private static final String SQL_APPEND = "sql.append(\" {} \");";
    private static final String GET_MEMBER_VALUE = "org.laniakeamly.poseidon.framework.tools.ReflectUtils.getMemberValue({},{})";

    /**
     * 添加sql前的处理方法
     * @param formats
     * @return
     */
    public static final String sqlAppendProcess(String... formats) {
        StringBuilder value = new StringBuilder(StringUtils.format(ProvideConstant.SQL_APPEND, formats));
        Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            String group = matcher.group(1);
            if (!group.contains(".")) {
                group = StringUtils.format(PARAMS_MAP_GET, group);
                group = group.substring(0,group.length()-1);
                value.append(StringUtils.format(PARAMS_LIST_ADD, group));
            } else {
                group = getMemberValue(group);
                value.append(StringUtils.format(PARAMS_LIST_ADD, group));
            }
        }
        if(value.subSequence(value.length()-3,value.length()-2).equals(";")){
            value.delete(value.length()-3,value.length()-2);
        }
        return value.toString().replaceAll("\\{\\{(.*?)}}","?");
    }

    /**
     * 获取成员值
     * @param value
     * @return
     */
    public static final String getMemberValue(String value) {
        int lastIndex = value.lastIndexOf(".") + 1;
        String last = value.substring(lastIndex);
        String before = value.substring(0, lastIndex - 1);
        return StringUtils.format(GET_MEMBER_VALUE, before, "\"" + last + "\"");
    }

    /**
     * 获取Map Value
     * @param name
     * @return
     */
    public static final String getMapValue(String name){
        return StringUtils.format(PARAMS_MAP_GET,name);
    }

    public static final String addArrayParameter(List<String> args){
        StringBuilder createArray = new StringBuilder("new Object[]{");
        for (String arg : args) {
            createArray.append(arg).append(",");
        }
        int len = createArray.length();
        createArray.delete(len-1,len);
        createArray.append("}");
        return StringUtils.format(ProvideConstant.PARAMS_LIST_ADD,createArray.toString());
    }

    public static void main(String[] args) {
        String str = "user.object.name.xxx";
        System.out.println(getMemberValue(str));
    }

}

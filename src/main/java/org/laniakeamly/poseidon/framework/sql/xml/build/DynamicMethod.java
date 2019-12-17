package org.laniakeamly.poseidon.framework.sql.xml.build;

import jdk.nashorn.internal.parser.Token;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

/**
 * 动态方法，需要在调用时被加载的JVM中，以提供获取动态sql
 * Create by 2BKeyboard on 2019/12/17 17:56
 */
public class DynamicMethod {

    private StringBuilder method = new StringBuilder();

    public DynamicMethod(String name){
        method.append(StringUtils.format("public String {} (Map map)",name));
        method.append("{");
        method.append("StringBuilder sql = new StringBuilder();");
    }

    /**
     * 添加sql
     * @param sql
     */
    public void append(String sql){
        method.append(StringUtils.format("sql.append(\"{}\");",sql));
    }

    /**
     * 添加if条件表达式
     * @param test
     * @param content
     */
    public void addif(String test, String content){
        method.append(StringUtils.format("if({})",test));
        method.append("{");
        method.append(content);
        method.append("}");
    }

}

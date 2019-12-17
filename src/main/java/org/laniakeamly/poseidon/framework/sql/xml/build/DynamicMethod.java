package org.laniakeamly.poseidon.framework.sql.xml.build;

import jdk.nashorn.internal.parser.Token;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * 动态方法，需要在调用时被加载的JVM中，以提供获取动态sql
 * Create by 2BKeyboard on 2019/12/17 17:56
 */
public class DynamicMethod {

    private StringBuilder method = new StringBuilder();

    public DynamicMethod(String name) {
        method.append(StringUtils.format("public String {} (Map map)", name));
        method.append("{");
        method.append("StringBuilder sql = new StringBuilder();");
    }

    /**
     * 添加sql
     * @param sql
     */
    public void append(String sql) {
        method.append(StringUtils.format("sql.append(\"{}\");", sql));
    }

    /**
     * 添加if条件表达式
     * @param ieValue
     */
    public void addif(IEValue ieValue) {
        List<String> tests = ieValue.getTests();
        List<String> contents = ieValue.getIfContent();
        List<String> elseContents = ieValue.getElseContent();
        int elseContentsSize = elseContents.size();
        for (int i = 0, len = contents.size(); i < len; i++) {
            method.append(StringUtils.format("if({})", tests.get(i)));
            method.append("{");
            method.append(StringUtils.format("sql.append(\"{}\");", contents.get(i)));
            method.append("}");
            if (i < elseContentsSize) {
                String elseContent = elseContents.get(i);
                if (elseContent != null) {
                    method.append("else{");
                    method.append(StringUtils.format("sql.append(\"{}\");", elseContent));
                    method.append("}");
                }
            }
        }

    }

    @Override
    public String toString() {
        method.append("}");
        return method.toString();
    }
}

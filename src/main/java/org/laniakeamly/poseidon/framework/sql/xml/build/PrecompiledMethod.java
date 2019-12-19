package org.laniakeamly.poseidon.framework.sql.xml.build;

import lombok.Getter;
import lombok.Setter;
import org.laniakeamly.poseidon.framework.sql.ProvideConstant;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 动态方法，需要在调用时被加载的JVM中，以提供获取动态sql
 * Create by 2BKeyboard on 2019/12/17 17:56
 */
@SuppressWarnings("SpellCheckingInspection")
public class PrecompiledMethod {

    @Getter
    private String name;

    @Getter
    @Setter
    private boolean load = false;

    @Getter
    @Setter
    private Method iMethod;

    @Setter
    private Object execute;

    private StringBuilder method = new StringBuilder();

    public PrecompiledMethod(String name) {
        this.name = name;
        method.append(StringUtils.format("public java.lang.String {} (java.util.Map map,java.util.List "+ ProvideConstant.SQL_PARAMS_SET+")", name));
        method.append("{");
        method.append("StringBuilder sql = new StringBuilder();");
    }

    public void append(String str) {
        method.append(str);
    }

    /**
     * 执行方法
     * @param map
     * @param params
     * @return
     */
    public String invoke(Map<String,Object> map,List params){
        try {
            return (String) iMethod.invoke(execute,map,params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加sql
     * @param sql
     */
    public void appendSql(String sql) {
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
        method.append("sql.toString();}");
        return method.toString();
    }
}

package org.laniakeamly.recycle.build;

import lombok.Getter;
import lombok.Setter;
import org.laniakeamly.poseidon.framework.sql.ProvideConstant;
import org.laniakeamly.poseidon.framework.tools.ReflectUtils;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

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
    private String result;

    @Getter
    private String type;

    @Getter
    @Setter
    private boolean load = false;

    @Getter
    @Setter
    private Method iMethod;

    @Setter
    private Object execute;

    private StringBuilder method = new StringBuilder();

    /**
     * 创建预编译方法
     * @param name          mapper标签的name属性
     * @param result    mapper标签的return属性
     */
    public PrecompiledMethod(String name, String result,String type) {
        this.name = name;
        this.result = result;
        method.append(StringUtils.format("public java.lang.String {} (java.util.Map map,java.util.List " + ProvideConstant.SQL_PARAMS_SET + ")", name));
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
    public String invoke(Map<String, Object> map, List params) {
        try {
            // return (String) iMethod.invoke(execute,map,params);
            return (String) ReflectUtils.invoke(iMethod, execute, map, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            method.append(ProvideConstant.sqlAppendProcess(contents.get(i)));
            method.append("}");
            if (i < elseContentsSize) {
                String elseContent = elseContents.get(i);
                if (elseContent != null) {
                    method.append("else{");
                    method.append(ProvideConstant.sqlAppendProcess(elseContent));
                    method.append("}");
                }
            }
        }

    }

    public String toString() {
        return method.toString();
    }
}

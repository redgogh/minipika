package org.raniaia.poseidon.framework.sql.xml.build;

import lombok.Getter;
import lombok.Setter;
import org.raniaia.poseidon.framework.model.Metadata;
import org.raniaia.poseidon.framework.ProvideConstant;
import org.raniaia.poseidon.framework.sql.TemplateLabel;
import org.raniaia.poseidon.framework.tools.ReflectUtils;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 动态方法，需要在调用时被加载的JVM中，以提供获取动态sql
 * Copyright: Create by TianSheng on 2019/12/17 17:56
 */
@SuppressWarnings("SpellCheckingInspection")
public class PrecompiledMethod {

    @Getter
    private String name;

    @Getter
    private Class<?> result;

    @Getter
    private TemplateLabel type;

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
     *
     * @param name   mapper标签的name属性
     * @param result mapper标签的return属性
     * @param type   mapper标签类型
     */
    public PrecompiledMethod(String name, String result, String type) {
        if("select".equals(type)){
            this.type = TemplateLabel.SELECT;
        }else{
            this.type = TemplateLabel.UPDATE;
        }
        try {
            this.name = name;
            if (!StringUtils.isEmpty(result)) {
                if (result.contains("(")) {
                    String prefix = result.substring(0, result.indexOf("("));
                    // 判断是不是java.lang包下的内容
                    Pattern pattern = Pattern.compile(prefix + "\\((.*?)\\)");
                    Matcher matcher = pattern.matcher(result);
                    while (matcher.find()) {
                        String langClass = matcher.group(1);
                        if (!StringUtils.isEmpty(langClass)) {
                            this.result = Class.forName("java." + prefix + "." + langClass);
                        }
                        break;
                    }
                }
                // 如果不是默认model包下的内容
                else if (!StringUtils.isEmpty(result)) {
                    this.result = Class.forName(Metadata.getModelClass(result).getName());
                }
            }
            method.append(StringUtils.format("public java.lang.String {} (java.util.Map map,java.util.List " + ProvideConstant.SQL_PARAMS_SET + ")", name));
            method.append("{");
            method.append("java.lang.StringBuilder sql = new java.lang.StringBuilder();");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public PrecompiledMethod append(String str) {
        method.append(str);
        return this;
    }

    /**
     * 执行方法
     *
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
     *
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

    public String getMethodString() {
        method.append("return sql.toString();}");
        return method.toString();
    }

    public PrecompiledMethod remove(int startPos, int endPos) {
        method.delete(startPos, endPos);
        return this;
    }

    public int length() {
        return method.length();
    }

}

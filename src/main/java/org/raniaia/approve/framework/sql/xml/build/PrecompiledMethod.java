package org.raniaia.approve.framework.sql.xml.build;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on 2019/12/17.
 */

import lombok.Getter;
import lombok.Setter;
import org.raniaia.approve.components.entity.publics.Metadata;
import org.raniaia.approve.framework.provide.ProvideVar;
import org.raniaia.approve.framework.sql.QueryTag;
import org.raniaia.approve.framework.tools.ReflectUtils;
import org.raniaia.approve.framework.tools.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 动态方法，需要在调用时被加载的JVM中，以提供获取动态sql
 * @author tiansheng
 */
@SuppressWarnings("SpellCheckingInspection")
public class PrecompiledMethod {

    @Getter
    private String name;

    @Getter
    private Class<?> result;

    @Getter
    private QueryTag type;

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
            this.type = QueryTag.SELECT;
        }else{
            this.type = QueryTag.UPDATE;
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
                // 如果不是默认entity包下的内容
                else if (!StringUtils.isEmpty(result)) {
                    this.result = Class.forName(Objects.requireNonNull(Metadata.getEntityClass(result),
                            "Error PrecompiledMethod. Cause: Your entity class configuration path is wrong.").getName());
                }
            }
            method.append(StringUtils.format("public java.lang.String {} (java.util.Map map,java.util.List " + ProvideVar.SQL_PARAMS_SET + ")", name));
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
            method.append(ProvideVar.sqlAppendProcess(contents.get(i)));
            method.append("}");
            if (i < elseContentsSize) {
                String elseContent = elseContents.get(i);
                if (elseContent != null) {
                    method.append("else{");
                    method.append(ProvideVar.sqlAppendProcess(elseContent));
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

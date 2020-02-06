package org.poseidon.framework.sql.xml.builder;

/**
 * Copyright: Create by TianSheng on 2019/12/13 15:47
 */
public interface CodeBuilderFactory {

    ClassBuilder methodToClassBody();

    /**
     * 创建类的声明
     * @return
     */
    ClassBuilder createClassStatement();

    /**
     * 添加方法
     * @param methodBuilder 方法对象
     * @return CodeBuilder
     */
    ClassBuilder putMethod(MethodBuilder methodBuilder);

}

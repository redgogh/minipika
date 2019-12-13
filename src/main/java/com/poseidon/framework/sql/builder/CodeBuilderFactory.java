package com.poseidon.framework.sql.builder;

/**
 * Create by 2BKeyboard on 2019/12/13 15:47
 */
public interface CodeBuilderFactory {

    /**
     * 创建对象
     *
     * @param       variable    对象名
     * @param       type        对象类型,全路径例如：java.lang.String
     *
     * @return      CodeBuilder
     */
    ClassBuilder createObject(String variable, String type);

    /**
     * 调用方法
     *
     * @param       variable      对象名
     * @param       method        方法名
     *
     * @return      CodeBuilder
     */
    ClassBuilder transfer(String variable, String method);

    /**
     * 调用方法
     *
     * @param       variable      对象名
     * @param       method        方法名
     * @param       args          参数,仅限String
     * @return      CodeBuilder
     */
    ClassBuilder transfer(String variable, String method, String... args);

    /**
     * 添加方法
     * @param   methodBuilder 方法对象
     * @return  CodeBuilder
     */
    ClassBuilder addMethod(MethodBuilder methodBuilder);

}

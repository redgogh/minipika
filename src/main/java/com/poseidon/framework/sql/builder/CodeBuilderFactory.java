package com.poseidon.framework.sql.builder;

/**
 * Create by 2BKeyboard on 2019/12/13 15:47
 */
public interface CodeBuilderFactory {

    /**
     * 创建方法
     *
     * @param       modify      修饰符,传入String,需要空格分隔例如："public {} static name"
     *                          其中 {} 代表返回对象的转移符
     * @param       args        参数,也需要手写例如："java.lang.String a,java.lang.Integer b"
     * @param       _return     返回对象,全路径
     *
     * @return      CodeBuilder
     */
    CodeBuilder createMethod(String modify,String args,String _return);

    /**
     * 创建对象
     *
     * @param       variable    对象名
     * @param       type        对象类型,全路径例如：java.lang.String
     *
     * @return      CodeBuilder
     */
    CodeBuilder createObject(String variable,String type);

    /**
     * 调用方法
     *
     * @param       variable      对象名
     * @param       method        方法名
     *
     * @return      CodeBuilder
     */
    CodeBuilder transfer(String variable,String method);

    /**
     * 调用方法
     *
     * @param       variable      对象名
     * @param       method        方法名
     * @param       args          参数,仅限String
     * @return      CodeBuilder
     */
    CodeBuilder transfer(String variable,String method,String... args);

}

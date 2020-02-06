package org.laniakeamly.poseidon.framework.sql.xml.builder;

/**
 * Copyright: Create by 2BKeyboard on 2019/12/13 23:59
 */
public interface MethodBuilderFacotry {

    /**
     * 创建方法
     *
     * @param       modify      修饰符,传入String,需要空格分隔例如："public {} static name"
     *                          其中 {} 代表返回对象的转移符
     * @param       args        modify中需要format的数据
     * @param       _return     返回对象,全路径
     *
     * @return      CodeBuilder
     */
    MethodBuilder createMethod(String modify,String _return,String... args);

}

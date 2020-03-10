package org.laniakeamly.poseidon.framework.logger;

import org.laniakeamly.poseidon.extension.Logger;
import org.laniakeamly.poseidon.framework.tools.DateUtils;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

/**
 *
 * Poseidon logger, this logger not support save to local. just
 * show info or debug or other format.
 *
 * If you want save to local, you can use other logger, such as log-back or else logger
 * framework you can import these ones logger framework.
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class PoseidonLogger implements Logger {

    /**
     * 第一个需要格式化的内容为时间
     * 第二个为类的路径
     * 第三个为调用方法
     * 第四个为调用行数
     * 第五个为输出类型
     * 第六个为输出信息
     */
    static String template = "{} | {} {}:{} | [{}] - {}";

    @Override
    public void info(String msg) {
        System.out.println(getTemplate("INFO",msg));
    }

    @Override
    public void warn(String msg) {
        System.err.println(getTemplate("WARN",msg));
    }

    @Override
    public void debug(String msg) {
        System.out.println(getTemplate("DEBUG",msg));
    }

    @Override
    public void error(String msg) {
        System.err.println(getTemplate("ERROR",msg));
    }

    private String getTemplate(String type,String info){
        StackTraceElement e = Thread.currentThread().getStackTrace()[3];
        return StringUtils.format(template,
                DateUtils.getInstance().formatNow(),
                e.getClassName(),
                e.getMethodName(),
                e.getLineNumber(),
                type,
                info);
    }

}

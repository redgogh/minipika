package com.tractor.db;

/**
 * 能够避免让你买飞机票的类!!!
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/27 15:21
 * @since 1.8
 */
public interface SaviorJdbcService {

    /**
     * 回滚到上一步操作
     */
    void rollback();

    /**
     * 回滚到指定步骤
     * @param step
     */
    void rollback(int step);

    /**
     * 操作日志
     */
    void log();

}

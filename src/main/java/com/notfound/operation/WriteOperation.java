package com.notfound.operation;

/**
 * 写入操作
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/4 14:13
 * @since 1.8
 */
public interface WriteOperation {

    /**
     * 插入一条数据
     * @param model
     * @return
     */
    int insert(Object model);

    /**
     * 更新一条数据
     * @param model
     * @return
     */
    int update(Object model);

    /**
     * 删除一条数据
     * @param id
     * @return
     */
    int delete(int id);

}

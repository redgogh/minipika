package org.poseidon.local_database;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.laniakeamly.poseidon.extension.ConnectionPool;
import org.laniakeamly.poseidon.framework.annotation.LocalModel;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.monitor.AbstractLocalModel;
import org.laniakeamly.poseidon.framework.monitor.Database;

/**
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Getter
@Setter
@LocalModel("connection_pool")
public class ConnectionPoolModel extends AbstractLocalModel {

    private int max;
    private int min;

    public static void main(String[] args) {
        Database database = BeansManager.getBean("database");
        ConnectionPoolModel cpm1 = new ConnectionPoolModel();
        cpm1.setMax(10);
        cpm1.setMin(2);
        database.insert(cpm1);
        ConnectionPoolModel cpm2 = new ConnectionPoolModel();
        cpm2.setMax(20);
        cpm2.setMin(10);
        database.insert(cpm2);

        // 查询所有数据
        System.out.println(JSON.toJSONString(database.queryForList(ConnectionPoolModel.class)));

    }

}

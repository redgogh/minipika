package org.poseidon.column;

import org.junit.Test;
import org.raniaia.poseidon.framework.context.PoseContextApplication;
import org.raniaia.poseidon.framework.db.JdbcSupport;
import org.raniaia.poseidon.components.model.database.ColumnPo;

import java.util.List;

/**
 * Copyright by tiansheng on 2020/2/15 1:47
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class Test0 {

    JdbcSupport jdbc = PoseContextApplication.getBean("jdbc");

    @Test
    public void getColumn() {
        List<ColumnPo> columnModels = jdbc.queryForList("show full columns from kkb_example_model", ColumnPo.class);
        System.out.println(columnModels.toString());
    }

}

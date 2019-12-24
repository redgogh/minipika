package org.laniakeamly.keyboard.mapper;

import org.junit.Test;
import org.laniakeamly.poseidon.framework.sql.Parameter;
import org.laniakeamly.poseidon.framework.sql.SqlMapper;

import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/24 1:27
 */
public class MapperTest {

    SqlMapper mapper = new SqlMapper("user_iakea");

    @Test
    public void builderSqlMapepr(){

        mapper.build("insertUserModel", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {

                map.put("username","张三");

            }
        });

    }

}

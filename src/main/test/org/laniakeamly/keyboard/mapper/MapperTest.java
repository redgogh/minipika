package org.laniakeamly.keyboard.mapper;

import org.junit.Test;
import org.laniakeamly.poseidon.framework.sql.Parameter;
import org.laniakeamly.poseidon.framework.sql.SqlMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/24 1:27
 */
public class MapperTest {

    SqlMapper mapper = new SqlMapper("user_iakea");

    @Test
    public void builderSqlMapepr(){

        List<String> list = new ArrayList<>();
        list.add("aaa");

        mapper.build("insertUserModel", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {

                map.put("username","张三");
                map.put("user",list);

            }
        });

    }

}

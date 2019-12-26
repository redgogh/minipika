package org.laniakeamly.keyboard.mapper;

import org.junit.Test;
import org.laniakeamly.poseidon.experiment.UserModel;
import org.laniakeamly.poseidon.framework.annotation.Valid;
import org.laniakeamly.poseidon.framework.sql.Parameter;
import org.laniakeamly.poseidon.framework.sql.SqlExecute;
import org.laniakeamly.poseidon.framework.sql.SqlMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/24 1:27
 */
public class MapperTest {

    SqlMapper mapper = new SqlMapper("userIakea");

    @Test
    public void builderSqlMapper(){

        UserModel userModel = new UserModel();
        List<UserModel> list = new ArrayList<>();
        userModel.setUserName("namexxx");
        userModel.setAddress("addxxxx");
        list.add(userModel);

        SqlExecute execute = mapper.build("insertUserModel", new Parameter() {
            @Override
            public void loader(Map<String, Object> map) {

                map.put("username","张三");
                map.put("users",list);

            }
        });

        execute.insert();

    }

}

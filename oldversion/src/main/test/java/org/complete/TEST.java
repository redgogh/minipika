package org.complete;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ************************************************************************/

/*
 * Creates on 2020/3/24 0:17
 */

import com.alibaba.fastjson.JSONObject;
import org.complete.entity.User;
import org.complete.mapper.UserMapper;
import org.jiakesimk.minipika.BeansManager;
import org.jiakesimk.minipika.components.config.ConfigLoader;
import org.jiakesimk.minipika.framework.provide.Minipika;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public class TEST {

    /**
     * 注入JDBC操作类
     */
    @Inject
    private JdbcSupport jdbc;

    /**
     * 实例化Mapper映射器对象， mapper xml文件对应地址在test/java/resources/compete目录下
     */
    UserMapper mapper = BeansManager.newInstance(UserMapper.class);

    public static void main(String[] args) {
        ConfigLoader.loadConfig("classpath:/compete/minipika.cfg");
        TEST test = BeansManager.newInstance(TEST.class);
        // 测试user对象insert
        test.userInsertByObject();
        // 测试user mapper映射器c
        test.findUserById(1);
    }

    public void userInsertByObject(){
        jdbc.insert(new User("test1","test1","test1@approve.com"));
    }

    public void findUserById(int id){
        Object o = mapper.findUserById(id);
        System.out.println(JSONObject.toJSONString(o));
    }

}

package com.test;

/*
 * Creates on 2019/11/13.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @author lts
 */
public class Task {

  @Test
  public void task() {
    UserMapper mapper = UserMapper.mapper;
    System.out.println(JSON.toJSONString(mapper.findUser("zhangsan")));
  }

}

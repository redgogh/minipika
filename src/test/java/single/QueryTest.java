package single;

/*
 * Creates on 2019/11/13.
 */

import com.alibaba.fastjson.JSONObject;
import mapper.UserMapper;
import org.junit.Test;

/**
 * @author lts
 */
public class QueryTest {

  UserMapper mapper = UserMapper.mapper;

  @Test
  public void queryList() {
    System.out.println(JSONObject.toJSONString(mapper.findAll()));
  }

  @Test
  public void queryObject() {
    System.out.println(JSONObject.toJSONString(mapper.findUserList("name1")));
  }

}

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
public class QueryListTest {

  UserMapper mapper = UserMapper.mapper;

  @Test
  public void test() {
//    System.out.println(JSONObject.toJSONString(mapper.findUserList("name1")));
    System.out.println(JSONObject.toJSONString(mapper.findAll()));
  }

}

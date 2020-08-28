package jdbc

import com.alibaba.fastjson.JSONObject
import org.jiakesiws.minipika.framework.factory.ClassLoaders
import org.junit.Test

class Query
{

  UserMapper userMapper = ClassLoaders.forMapper(UserMapper)

  @Test
  void queryUser()
  {
    println JSONObject.toJSONString(userMapper.queryUser('aa'))
  }

}

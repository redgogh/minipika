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
    List<UserInfo> infoList = userMapper.queryUser('a')
    println JSONObject.toJSONString(infoList)
  }

}

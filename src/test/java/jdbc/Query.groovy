package jdbc

import org.jiakesiws.minipika.framework.factory.ClassLoaders
import org.jiakesiws.minipika.framework.utils.FastJsonHelper
import org.junit.Test

class Query
{

  UserMapper userMapper = ClassLoaders.forMapper(UserMapper)

  @Test
  void queryUser()
  {
    List<UserInfo> infoList = userMapper.queryUser('a')
    println FastJsonHelper.toJSONString(infoList)
  }

}

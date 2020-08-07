package entity

import com.alibaba.fastjson.JSONObject
import org.jiakesiws.minipika.framework.factory.Factorys
import org.jiakesiws.minipika.framework.utils.Arrays
import org.junit.Test

class KtTest {

  private val mapper = Factorys.forMapper(MqlMapper::class.java)

  @Test
  fun findUserTest() {
    val result = mapper.findUser(User("update1", "22"))
    println(JSONObject.toJSONString(result))
  }

  @Test
  fun addBatchTest() {
    val u1 = User("kotlin1", "kt1")
    val u2 = User("kotlin2", "kt2")
    println(Arrays.toString(mapper.addBatch(arrayListOf(u1, u2))))
  }

}
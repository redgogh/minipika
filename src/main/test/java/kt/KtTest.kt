package kt

import com.alibaba.fastjson.JSONObject
import org.jiakesimk.minipika.framework.factory.Factorys
import org.jiakesimk.minipika.framework.util.ArrayUtils
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
    println(ArrayUtils.toString(mapper.addBatch(arrayListOf(u1, u2))))
  }

}
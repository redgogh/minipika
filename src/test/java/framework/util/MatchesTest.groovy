package framework.util

import org.jiakesiws.minipika.framework.utils.Matches
import org.junit.Test

class MatchesTest
{

  @Test
  void test()
  {
    String sql = "where username like(%#{name}%)"
    Matches.find(sql, "like\\((.*?)\\)", value -> {
      println value
      def v = value as String
      println 'concat(' + v.replace("%", "'%'") + ")"
    })
  }

}

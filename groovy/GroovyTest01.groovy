package groovy

import org.jiakesiws.minipika.framework.util.Maps

class GroovyTest01 {

  static void main(String[] args) {
    Map<String, Integer> map = Maps.newHashMap();
    map.put("a", 1)
    println map.a
    def s = "a"
    map.a = 2
    println map.a
  }

}

package org.jiakesimk.minipika.framework.util

class Matches {

  static String[] matches(character, r, Closure closure) {
    def group = []
    def m = character =~r
    m.find().each {
      def value = m.group()
      if(StringUtils.isNotEmpty(value)) {
        group.add(closure.curry(value))
      }
    }
   return group
  }

}

package groovy

import org.jiakesimk.minipika.components.annotation.QueryOf
import org.jiakesimk.minipika.components.annotation.QueryOf

interface TestFace {

  @QueryOf("")
  def addUser()

  def select()

}
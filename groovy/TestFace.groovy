package groovy

import org.jiakesiws.minipika.components.annotation.QueryOf
import org.jiakesiws.minipika.components.annotation.QueryOf

interface TestFace {

  @QueryOf("")
  def addUser()

  def select()

}
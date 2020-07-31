package org.jiakesimk.minipika.components.wrapper;

/*
 * Creates on 2019/11/13.
 */

/**
 * @author lts
 */
public interface IdenPool {

  String EMPTY          = "";
  String AND            = "and";
  String FALSE          = "false";
  String TRUE           = "true";
  String GE             = ">=";
  String GT             = ">";
  String LE             = "<=";
  String LT             = "<";
  String EQUALS         = "=";
  String LIKE           = "like";
  String R_LIKE         = LIKE.concat(" '{}%'");
  String L_LIKE         = LIKE.concat(" '%{}'");
  String PLUS           = "+";
  String SUB            = "-";
  String MUL            = "*";
  String DIV            = "/";


}

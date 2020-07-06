//package org.minipika.commponents.proxy;
//
//import java.lang.*;
//import java.util.*;
//import java.lang.*;
//import java.math.*;
//
//import org.jiakesimk.minipika.framework.util.*;
//
//@SuppressWarnings("unchecked")
//public class $MqlMapper {
//  public Object[] findUser(groovy.User user) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("select * from website_user_info where 1=1 ");
//    if (StringUtils.isNotEmpty(Fields.getValue(user, "username")) && Fields.getValue(user, "username") != null) {
//      sql.append("and username = ? ");
//      arguments.add(Fields.getValue(user, "username"));
//    }
//    sql.append(" ");
//    Object[] objects = new Object[2];
//    objects[0] = sql.toString();
//    objects[1] = arguments;
//    return objects;
//  }
//
//  public Object[] addUser(groovy.User user) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("insert into user (username) values (?) ");
//    arguments.add(Fields.getValue(user, "username"));
//    sql.append(" ");
//    Object[] objects = new Object[2];
//    objects[0] = sql.toString();
//    objects[1] = arguments;
//    return objects;
//  }
//
//  public Object[] addBatch(java.util.List users) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("insert into user (username) values (?,  ?) ");
//    for (Object user : users) {
//      Object[] singleArgs = new Object[2];
//      singleArgs[0] = Fields.getValue(user, "username");
//      singleArgs[1] = Fields.getValue(user, "password");
//      arguments.add(singleArgs);
//    }
//    sql.append(" ");
//    Object[] objects = new Object[2];
//    objects[0] = sql.toString();
//    objects[1] = arguments;
//    return objects;
//  }
//}
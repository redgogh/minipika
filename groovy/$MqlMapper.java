//package org.minipika.components.proxy;
//
//import java.lang.*;
//import java.util.*;
//import java.lang.*;
//import java.math.*;
//
//import org.jiakesimk.minipika.framework.util.*;
//import org.jiakesimk.minipika.framework.util.agent.*;
//
//@SuppressWarnings("unchecked")
//public class $UserMapper {
//  public Object[] findUser(java.lang.String arg0) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("select * from website_user_info where 1=1 ");
//    if (AgentStringUtils.isNotEmpty(username)) {
//      sql.append("and username = ? ");
//      arguments.add(username);
//    }
//    sql.append(" ");
//    Object[] objects = new Object[2];
//    objects[0] = sql.toString();
//    objects[1] = arguments;
//    return objects;
//  }
//
//  public Object[] addBatch(java.util.List arg0) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("insert into website_user_info (username,  `password`) values (?,  ?) ");
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
//
//  public Object[] findUserList(java.lang.String arg0) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("select * from website_user_info where 1=1 ");
//    if (AgentStringUtils.isNotEmpty(username)) {
//      sql.append("and username = ? ");
//      arguments.add(username);
//    }
//    sql.append(" ");
//    Object[] objects = new Object[2];
//    objects[0] = sql.toString();
//    objects[1] = arguments;
//    return objects;
//  }
//}

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
//public class $MqlMapper {
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
//  public Object[] findUser(java.lang.Object arg0) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("select * from website_user_info where 1=1 ");
//    if (AgentStringUtils.isNotEmpty(Fields.getValue(user, "username")) && Fields.getValue(user, "username") != null) {
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
//  public Object[] updateUser(java.lang.Object arg0, java.lang.Object arg1) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("UPDATE `website_user_info` SET `username` = ?,  `password` = ? WHERE `id` = ?; ");
//    arguments.add(Fields.getValue(user, "username"));
//    arguments.add(Fields.getValue(user, "password"));
//    arguments.add(id);
//    sql.append(" ");
//    Object[] objects = new Object[2];
//    objects[0] = sql.toString();
//    objects[1] = arguments;
//    return objects;
//  }
//
//  public Object[] addUser(java.lang.Object arg0) {
//    StringBuilder sql = new StringBuilder();
//    List arguments = new LinkedList();
//    sql.append(" ");
//    sql.append("insert into website_user_info (username,  `password`) ");
//    sql.append("values (?, ?) ");
//    arguments.add(Fields.getValue(user, "username"));
//    arguments.add(Fields.getValue(user, "password"));
//    sql.append(" ");
//    Object[] objects = new Object[2];
//    objects[0] = sql.toString();
//    objects[1] = arguments;
//    return objects;
//  }
//}

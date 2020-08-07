package org.jiakesiws.minipika.components.proxy;

import java.lang.*;
import java.util.*;
import java.lang.*;
import java.math.*;

import org.jiakesiws.minipika.framework.utils.*;
import org.jiakesiws.minipika.framework.utils.agent.*;

@SuppressWarnings("unchecked")
public class $UserMapper {
  public Object[] update(java.lang.Object name, java.lang.Object newName) {
    StringBuilder sql = new StringBuilder();
    List arguments = new LinkedList();
    sql.append(" ");
    sql.append("update website_user_info set username = ? ");
    arguments.add(newName);
    sql.append("where username = ? ");
    arguments.add(name);
    sql.append(" ");
    Object[] objects = new Object[2];
    objects[0] = sql.toString();
    objects[1] = arguments;
    return objects;
  }

  public Object[] findAll)

  {
    StringBuilder sql = new StringBuilder();
    List arguments = new LinkedList();
    sql.append(" ");
    sql.append("select * from website_user_info ");
    sql.append(" ");
    Object[] objects = new Object[2];
    objects[0] = sql.toString();
    objects[1] = arguments;
    return objects;
  }

  public Object[] findUserList(java.lang.Object username) {
    StringBuilder sql = new StringBuilder();
    List arguments = new LinkedList();
    sql.append(" ");
    sql.append("select * from website_user_info where 1=1 ");
    if (AgentStringUtils.isNotEmpty(username)) {
      sql.append("and username = ? ");
      arguments.add(username);
    }
    sql.append(" ");
    Object[] objects = new Object[2];
    objects[0] = sql.toString();
    objects[1] = arguments;
    return objects;
  }

  public Object[] addBatch(java.util.List users, java.lang.Object name) {
    StringBuilder sql = new StringBuilder();
    List arguments = new LinkedList();
    sql.append(" ");
    sql.append("insert into website_user_info (username,  `password`) values (?,  ?) ");
    for (Object user : users) {
      Object[] singleArgs = new Object[2];
      singleArgs[0] = Fields.getValue(user, "username");
      singleArgs[1] = Fields.getValue(user, "password");
      arguments.add(singleArgs);
    }
    sql.append(" ");
    Object[] objects = new Object[2];
    objects[0] = sql.toString();
    objects[1] = arguments;
    return objects;
  }

  public Object[] findUser(java.lang.Object username) {
    StringBuilder sql = new StringBuilder();
    List arguments = new LinkedList();
    sql.append(" ");
    sql.append("select * from website_user_info where 1=1 ");
    if (AgentStringUtils.isNotEmpty(username)) {
      sql.append("and username = ? ");
      arguments.add(username);
    }
    sql.append(" ");
    Object[] objects = new Object[2];
    objects[0] = sql.toString();
    objects[1] = arguments;
    return objects;
  }
}
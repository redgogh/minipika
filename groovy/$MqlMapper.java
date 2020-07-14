package org.minipika.components.proxy;

import java.lang.*;
import java.util.*;
import java.lang.*;
import java.math.*;

import org.jiakesimk.minipika.framework.util.*;
import org.jiakesimk.minipika.framework.util.agent.*;

@SuppressWarnings("unchecked")
public class $MqlMapper {
  public Object[] findUser(kt.User user) {
    StringBuilder sql = new StringBuilder();
    List arguments = new LinkedList();
    sql.append(" ");
    sql.append("select * from website_user_info where 1=1 ");
    if (AgentStringUtils.isNotEmpty(Fields.getValue(user, "username")) && Fields.getValue(user, "username") != null) {
      sql.append("and username = ? ");
      arguments.add(Fields.getValue(user, "username"));
    }
    sql.append(" ");
    Object[] objects = new Object[2];
    objects[0] = sql.toString();
    objects[1] = arguments;
    return objects;
  }
}
package org.jiakesimk.minipika.components.jdbc;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Creates on 2020/03/24.
 */

import org.jiakesimk.minipika.components.jdbc.transaction.TransactionFactory;
import org.jiakesimk.minipika.components.logging.Log;
import org.jiakesimk.minipika.components.logging.LogFactory;
import org.jiakesimk.minipika.framework.exception.MinipikaException;
import org.jiakesimk.minipika.framework.provide.ProvideVar;
import org.jiakesimk.minipika.framework.provide.Minipika;
import org.jiakesimk.minipika.components.config.GlobalConfig;
import org.jiakesimk.minipika.components.entity.publics.AbstractEntity;
import org.jiakesimk.minipika.framework.provide.component.Component;
import org.jiakesimk.minipika.framework.tools.SecurityManager;
import org.jiakesimk.minipika.components.entity.publics.Metadata;
import org.jiakesimk.minipika.components.entity.database.ColumnEntity;
import org.jiakesimk.minipika.framework.tools.JdbcUtils;
import org.jiakesimk.minipika.framework.tools.EntityUtils;
import org.jiakesimk.minipika.framework.tools.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Jdbc support.
 */
@Component
public class JdbcSupportImpl implements JdbcSupport {

  @Inject
  private NativeJdbc nativeJdbc;

  private static final Log log = LogFactory.getLog(JdbcSupportImpl.class);

  public JdbcSupportImpl() {
  }

  public JdbcSupportImpl(TransactionFactory transactionFactory) {
    nativeJdbc.setTransactionFactory(transactionFactory);
  }

  @Override
  public <T> T queryForObject(String sql, Class<T> obj, Object... args) {
    NativeResult result = nativeJdbc.executeQuery(sql, args);
    return result == null ? null : result.conversionJavaBean(obj);
  }

  @Override
  public <T> List<T> queryForList(String sql, Class<T> obj, Object... args) {
    NativeResult result = nativeJdbc.executeQuery(sql, args);
    return result == null ? null : result.conversionJavaList(obj);
  }

  @Override
  public <T> Set<T> queryForSet(String sql, Class<T> obj, Object... args) {
    return new HashSet<>(queryForList(sql, obj, args));
  }

  @Override
  public String queryForJson(String sql, Object... args) {
    NativeResult result = nativeJdbc.executeQuery(sql, args);
    return result == null ? null : result.toJSONString();
  }

  @Override
  public NativePageHelper queryForPage(String sql, NativePageHelper pageVo, Object... args) {
    int size = pageVo.getPageSize();
    int number = pageVo.getPageNum();
    int startPos = number * size;
    pageVo.setRecords(count(sql, args));
    StringBuilder value = new StringBuilder(sql);
    if ((value.lastIndexOf(";") + 1) == value.length()) {
      value.insert(value.length() - 1, StringUtils.format(" LIMIT {},{} ", startPos, size));
    } else {
      value.append(StringUtils.format(" LIMIT {},{} ", startPos, size));
    }
    NativeResult result = nativeJdbc.executeQuery(value.toString(), args);
    pageVo.setData(result.conversionJavaList(pageVo.getGeneric()));
    return pageVo;
  }

  @Override
  public NativeResult queryForResult(String sql, Object... args) {
    return nativeJdbc.executeQuery(sql, args);
  }

  @Override
  public int update(String sql, Object... args) {
    return nativeJdbc.executeUpdate(sql, args);
  }

  @Override
  public int updateByString(String sql) {
    return nativeJdbc.executeUpdate(sql);
  }

  @Override
  public int update(Object obj) {
    return update(obj, false);
  }

  @Override
  public int updateDoNULL(Object obj) {
    return update(obj, true);
  }

  @Override
  public int insert(Object obj) {
    checkCanSave(obj);
    List<Object> params = Lists.newArrayList();
    String sql = JdbcUtils.generateInsertSQL(obj, params);
    return nativeJdbc.executeUpdate(sql, params.toArray());
  }

  @Override
  public int[] insert(List<Object> entitys) {
    List<Object[]> params = Lists.newArrayList();
    String sql = JdbcUtils.generateInsertBatchSQL(entitys, params);
    return executeBatch(sql, params);
  }

  @Override
  public int insert(String sql, Object... args) {
    return nativeJdbc.executeUpdate(sql, args);
  }

  @Override
  public int count(Class<?> target) {
    if (SecurityManager.existEntity(target)) {
      String table = EntityUtils.getEntityAnnotation(target).value();
      return count("select count(*) from ".concat(table));
    }
    return 0;
  }

  @Override
  public int count(String sql, Object... args) {
    StringBuilder value = new StringBuilder(sql.toLowerCase());
    String select = "select";
    int selectPos = value.indexOf(select) + select.length();
    int fromPos = value.indexOf("from");
    value.replace(selectPos, fromPos, " count(*) ");
    NativeResult result = nativeJdbc.executeQuery(value.toString(), args);
    result.hasNext();
    String next = result.next();
    return Integer.parseInt(next == null ? "0" : next);
  }

  @Override
  public boolean execute(String sql, Object... args) {
    return nativeJdbc.execute(sql, args);
  }

  @Override
  public int[] executeBatch(String sql, List<Object[]> args) {
    return nativeJdbc.executeBatch(sql, args);
  }

  @Override
  public int[] executeBatch(String sql, Object[] args) {
    return nativeJdbc.executeBatch(sql, args);
  }

  @Override
  public int[] executeBatch(String[] sql, List<Object[]> args) {
    return nativeJdbc.executeBatch(sql, args);
  }

  @Override
  public List<String> getColumns(String tableName) {
    String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ? and table_schema = ?;";
    return nativeJdbc.executeQuery(sql, tableName, GlobalConfig.getConfig().getDbname()).conversionJavaList(String.class);
  }

  @Override
  public List<ColumnEntity> getColumnMetadata(String table) {
    String queryColumnsSql = StringUtils.format(ProvideVar.QUERY_COLUMNS, table);
    return queryForList(queryColumnsSql, ColumnEntity.class);
  }

  // 是否更新为NULL的字段是否更新为NULL的字段
  private int update(Object obj, boolean bool) {
    try {
      checkCanSave(obj);
      Class<?> target = obj.getClass();
      List<Object> params = Lists.newArrayList();
      StringBuffer buffer = new StringBuffer("update ");
      String table = EntityUtils.getEntityAnnotation(target).value();
      buffer.append("`").append(table).append("` set ");
      List<Field> fields = EntityUtils.getEntityField(obj);
      for (Field field : fields) {
        Object v = field.get(obj);
        if (!bool) {
          if (v != null) {
            buffer.append("`").append(EntityUtils.humpToUnderline(field.getName())).append("` = ?, ");
            params.add(v);
          }
        } else {
          buffer.append("`").append(EntityUtils.humpToUnderline(field.getName())).append("` = ?, ");
          params.add(v);
        }
      }
      int length = buffer.length();
      buffer.delete((length - 2), (length - 1));
      // 添加条件
      String pk = Metadata.getAttribute().get(table).getPk();
      Field field = target.getDeclaredField(pk);
      field.setAccessible(true);
      Object v = field.get(obj);
      buffer.append("where `".concat(pk).concat("` = ?"));
      params.add(v);
      return nativeJdbc.executeUpdate(buffer.toString(), params.toArray());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return 0;
  }

  private void checkCanSave(Object obj) {
    if (!AbstractEntity.getCanSave(obj)) {
      log.error("Error while executing update method. Cause: " +
              "some of your fields failed validating.");
      throw new MinipikaException("Error while executing update method. Cause: " +
              "some of your fields failed validating.");
    }
  }

}

package com.tengu.db;

import com.tengu.annotation.Model;
import com.tengu.config.Config;
import com.tengu.exception.ModelException;
import com.tengu.model.IndexModel;
import com.tengu.model.ModelAttribute;
import com.tengu.pool.ConnectionPool;
import com.tengu.tools.TenguUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/11 23:40
 * @since 1.8
 */
public class JdbcFunction extends NativeJdbc implements JdbcFunctionService {

    private static JdbcFunction template;

    private ConnectionPool pool = ConnectionPool.getPool();

    public static JdbcFunction getFunction() {
        if (template == null) {
            template = new JdbcFunction();
        }
        return template;
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> obj, Object... args) {
        return executeQuery(sql, args).conversionJavaBean(obj);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> obj, Object... args) {
        return executeQuery(sql, args).conversionJavaList(obj);
    }

    @Override
    public String queryForJson(String sql, Object... args) {
        return executeQuery(sql, args).toJSONString();
    }

    @Override
    public Integer update(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    @Override
    public Integer update(Object obj) {
        return update(obj, false);
    }

    @Override
    public Integer updateDoNULL(Object obj) {
        return update(obj, true);
    }

    @Override
    public Integer insert(Object obj) {
        List<Object> param = new ArrayList<>();
        try {
            StringBuffer into = new StringBuffer("insert into ");
            StringBuffer values = new StringBuffer(" values ");
            Class<?> target = obj.getClass();
            if (target.isAnnotationPresent(Model.class)) {
                Model model = target.getDeclaredAnnotation(Model.class);
                into.append("`").append(model.value()).append("`");
            }
            into.append("(");
            values.append("(");
            for (Field field : target.getDeclaredFields()) {
                field.setAccessible(true);
                Object v = field.get(obj);
                if (v != null) {
                    into.append("`").append(TenguUtils.humpToUnderline(field.getName())).append("`,");
                    values.append("?,");
                    param.add(v);
                }
            }
            into.deleteCharAt((into.length() - 1));
            into.append(")");
            values.deleteCharAt((values.length() - 1));
            values.append(")");
            into.append(values);
            return executeUpdate(into.toString(), param.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer insert(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    @Override
    public Integer delete(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    @Override
    public List<String> getColumns(String tableName) {
        String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ? and table_schema = ?;";
        return executeQuery(sql,tableName,Config.getDbname()).conversionJavaList(String.class);
    }

    @Override
    public List<IndexModel> getIndexes(String table) {
        return executeQuery("show index from `".concat(table).concat("`")).conversionJavaList(IndexModel.class);
    }

    /**
     * 是否更新为NULL的字段
     *
     * @param obj
     * @param bool
     * @return
     */
    public int update(Object obj, boolean bool) {
        try {
            Class<?> target = obj.getClass();
            if (!target.isAnnotationPresent(Model.class)) {
                throw new ModelException("对象不是实体");
            }
            List<Object> params = new ArrayList<>();
            StringBuffer buffer = new StringBuffer("update ");
            String table = target.getDeclaredAnnotation(Model.class).value();
            buffer.append("`").append(table).append("` set ");
            for (Field field : target.getDeclaredFields()) {
                field.setAccessible(true);
                Object v = field.get(obj);
                if (!bool) {
                    if (v != null) {
                        buffer.append("`").append(TenguUtils.humpToUnderline(field.getName())).append("` = ?, ");
                        params.add(v);
                    }
                } else {
                    buffer.append("`").append(TenguUtils.humpToUnderline(field.getName())).append("` = ?, ");
                    params.add(v);
                }
            }
            int length = buffer.length();
            buffer.delete((length - 2), (length - 1));
            // 添加条件
            String primaryKey = ModelAttribute.getAttribute().get(table).getPrimaryKey();
            Field field = target.getDeclaredField(primaryKey);
            field.setAccessible(true);
            Object v = field.get(obj);
            buffer.append("where `".concat(primaryKey).concat("` = ?"));
            params.add(v);
            return executeUpdate(buffer.toString(), params.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}

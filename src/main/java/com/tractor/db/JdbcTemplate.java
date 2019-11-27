package com.tractor.db;

import com.tractor.annotation.Model;
import com.tractor.config.Config;
import com.tractor.exception.TractorException;
import com.tractor.model.CriteriaManager;
import com.tractor.model.ModelAttribute;
import com.tractor.tools.TractorUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate extends NativeJdbc implements JdbcTemplateService {

    private static JdbcTemplate template;

    public static JdbcTemplate getTemplate() {
        if (template == null) {
            template = new JdbcTemplate();
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
    public int update(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    @Override
    public int updateForModel(Object obj) {
        return update(obj, false);
    }

    @Override
    public int updateDoNULL(Object obj) {
        return update(obj, true);
    }

    @Override
    public int insert(Object obj) {
        List<Object> param = new ArrayList<>();
        try {
            StringBuffer into = new StringBuffer("insert into ");
            StringBuffer values = new StringBuffer(" values ");
            Class<?> target = obj.getClass();
            if (CriteriaManager.existModel(target)) {
                Model model = TractorUtils.getModelAnnotation(target);
                into.append("`").append(model.value()).append("`");
            }
            into.append("(");
            values.append("(");
            for (Field field : target.getDeclaredFields()) {
                field.setAccessible(true);
                Object v = field.get(obj);
                if (v != null) {
                    into.append("`").append(TractorUtils.humpToUnderline(field.getName())).append("`,");
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
    public int insert(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    @Override
    public int delete(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    @Override
    public List<String> getColumns(String tableName) {
        String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ? and table_schema = ?;";
        return executeQuery(sql, tableName, Config.getDbname()).conversionJavaList(String.class);
    }

    /**
     * 是否更新为NULL的字段
     *
     * @param obj
     * @param bool
     * @return
     */
    private int update(Object obj, boolean bool) {
        try {
            Class<?> target = obj.getClass();
            List<Object> params = new ArrayList<>();
            StringBuffer buffer = new StringBuffer("update ");
            String table = TractorUtils.getModelAnnotation(target).value();
            buffer.append("`").append(table).append("` set ");
            for (Field field : target.getDeclaredFields()) {
                field.setAccessible(true);
                Object v = field.get(obj);
                if (!bool) {
                    if (v != null) {
                        buffer.append("`").append(TractorUtils.humpToUnderline(field.getName())).append("` = ?, ");
                        params.add(v);
                    }
                } else {
                    buffer.append("`").append(TractorUtils.humpToUnderline(field.getName())).append("` = ?, ");
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

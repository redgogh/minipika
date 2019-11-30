package com.tractor.framework.db;

import com.tractor.framework.annotation.Model;
import com.tractor.framework.config.Config;
import com.tractor.framework.model.SecurityManager;
import com.tractor.framework.model.Metadata;
import com.tractor.framework.tools.TractorUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Jdbc支持
 * Create by 2BKeyboard on 2019/11/12 10:35
 */
public class JdbcSupport implements JdbcSupportService {

    private final NativeJdbc nativeJdbc = NativeBeans.newNativeJdbc();

    public static JdbcSupport getTemplate() {
        return new JdbcSupport();
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> obj, Object... args) {
        return nativeJdbc.executeQuery(sql, args).conversionJavaBean(obj);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> obj, Object... args) {
        return nativeJdbc.executeQuery(sql, args).conversionJavaList(obj);
    }

    @Override
    public String queryForJson(String sql, Object... args) {
        return nativeJdbc.executeQuery(sql, args).toJSONString();
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
            if (SecurityManager.existModel(target)) {
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
            return nativeJdbc.executeUpdate(into.toString(), param.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int insert(String sql, Object... args) {
        return nativeJdbc.executeUpdate(sql, args);
    }

    @Override
    public long count(Class<?> target) {
        if (SecurityManager.existModel(target)) {
            return count(TractorUtils.getModelAnnotation(target).value());
        }
        return 0;
    }

    @Override
    public long count(String table) {
        NativeResult result = nativeJdbc.executeQuery("select count(*) from ".concat(table));
        result.hasNext();
        return Long.valueOf(result.next());
    }

    @Override
    public boolean execute(String sql, Object... args) {
        return nativeJdbc.execute(sql, args);
    }

    @Override
    public List<String> getColumns(String tableName) {
        String sql = "select COLUMN_NAME from information_schema.COLUMNS where table_name = ? and table_schema = ?;";
        return nativeJdbc.executeQuery(sql, tableName, Config.getDbname()).conversionJavaList(String.class);
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
            String primaryKey = Metadata.getAttribute().get(table).getPrimaryKey();
            Field field = target.getDeclaredField(primaryKey);
            field.setAccessible(true);
            Object v = field.get(obj);
            buffer.append("where `".concat(primaryKey).concat("` = ?"));
            params.add(v);
            return nativeJdbc.executeUpdate(buffer.toString(), params.toArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}

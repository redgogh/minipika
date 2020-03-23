package org.raniaia.poseidon.components.db;

import org.raniaia.poseidon.framework.provide.ProvideVar;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.components.config.GlobalConfig;
import org.raniaia.poseidon.components.model.publics.AbstractModel;
import org.raniaia.poseidon.framework.provide.component.Component;
import org.raniaia.poseidon.framework.tools.SecurityManager;
import org.raniaia.poseidon.components.model.publics.Metadata;
import org.raniaia.poseidon.components.model.database.ColumnPo;
import org.raniaia.poseidon.framework.tools.JdbcUtils;
import org.raniaia.poseidon.framework.tools.ModelUtils;
import org.raniaia.poseidon.framework.tools.StringUtils;

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

    @Valid
    private NativeJdbc nativeJdbc;

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
        if (!AbstractModel.getCanSave(obj)) return 0;
        List<Object> params = new ArrayList<>();
        String sql = JdbcUtils.generateInsertSQL(obj, params);
        return nativeJdbc.executeUpdate(sql, params.toArray());
    }

    @Override
    public int[] insert(List<Object> models) {
        List<Object[]> params = new ArrayList<>();
        String sql = JdbcUtils.generateInsertBatchSQL(models, params);
        return executeBatch(sql, params);
    }

    @Override
    public int insert(String sql, Object... args) {
        return nativeJdbc.executeUpdate(sql, args);
    }

    @Override
    public int count(Class<?> target) {
        if (SecurityManager.existModel(target)) {
            String table = ModelUtils.getModelAnnotation(target).value();
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
    public List<ColumnPo> getColumnMetadata(String table) {
        String queryColumnsSql = StringUtils.format(ProvideVar.QUERY_COLUMNS, table);
        return queryForList(queryColumnsSql, ColumnPo.class);
    }

    // 是否更新为NULL的字段是否更新为NULL的字段
    private int update(Object obj, boolean bool) {
        try {
            if (!AbstractModel.getCanSave(obj)) return 0;
            Class<?> target = obj.getClass();
            List<Object> params = new ArrayList<>();
            StringBuffer buffer = new StringBuffer("update ");
            String table = ModelUtils.getModelAnnotation(target).value();
            buffer.append("`").append(table).append("` set ");
            List<Field> fields = ModelUtils.getModelField(obj);
            for (Field field : fields) {
                Object v = field.get(obj);
                if (!bool) {
                    if (v != null) {
                        buffer.append("`").append(ModelUtils.humpToUnderline(field.getName())).append("` = ?, ");
                        params.add(v);
                    }
                } else {
                    buffer.append("`").append(ModelUtils.humpToUnderline(field.getName())).append("` = ?, ");
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

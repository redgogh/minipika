package com.tengu.model;

import com.tengu.annotation.Ignore;
import com.tengu.exception.TenguException;
import com.tengu.tools.TenguUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 18:12
 * @since 1.8
 */
public class TenguResultSet {

    private Map<String, String> resultSet;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public TenguResultSet() {
    }

    public TenguResultSet(ResultSet rset) {
        buildResultSet(rset);
    }

    /**
     * 构建TenguResultSet
     *
     * @param rset
     * @return
     * @throws Exception
     */
    public TenguResultSet buildResultSet(ResultSet rset) {
        try {
            rset.next();
            ResultSetMetaData mdata = rset.getMetaData();
            int len = mdata.getColumnCount();
            resultSet = new LinkedHashMap<>(len); // 初始化Map
            for (int i = 0; i < len; i++) {
                String name = mdata.getColumnName(i + 1);
                String value = rset.getString(name);
                resultSet.put(name, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public Map<String, String> getResultSet() {
        return this.resultSet;
    }

    /**
     * 将查询的结果转换为实体类
     * @param target
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T conversion(Class<T> target) {
        List<String> names = new ArrayList<>();
        T model = null;
        try {
            model = target.newInstance();
            for (Field field : target.getDeclaredFields()) names.add(field.getName());
            for (Map.Entry<String, String> v : resultSet.entrySet()) {
                String hump = TenguUtils.UnderlineToHump(v.getKey());
                if (!names.contains(hump)) continue;                                 // 判断Model中是否含有hump字段
                Field field = target.getDeclaredField(hump);
                if (field.isAnnotationPresent(Ignore.class)) continue;               // 判断字段是否存在Ignore注解
                field.setAccessible(true);
                setValue(field, v.getValue(), model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    public void setValue(Field field, String v, Object model) throws Exception {
        String type = field.getType().getName();
        if (type.equals("java.lang.Integer")) {
            field.set(model, Integer.valueOf(v));
        }
        if (type.equals("java.lang.Long")) {
            field.set(model, Long.valueOf(v));
        }
        if (type.equals("java.lang.Short")) {
            field.set(model, Short.valueOf(v));
        }
        if (type.equals("java.lang.Float")) {
            field.set(model, Float.valueOf(v));
        }
        if (type.equals("java.lang.Double")) {
            field.set(model, Double.valueOf(v));
        }
        if (type.equals("java.lang.Byte")) {
            field.set(model, Byte.valueOf(v));
        }
        if (type.equals("java.lang.Boolean")) {
            field.set(model, Boolean.valueOf(v));
        }
        if (type.equals("java.math.BigDecimal")) {
            field.set(model, new BigDecimal(v));
        }
        if (type.equals("java.math.BigInteger")) {
            field.set(model, new BigInteger(v));
        }
        if (type.equals("java.util.Date")) {
            field.set(model, formatter.format(v));
        }
        if (type.equals("java.lang.String")) {
            field.set(model, v);
        }
    }

}

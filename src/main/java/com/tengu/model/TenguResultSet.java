package com.tengu.model;

import com.mysql.cj.protocol.ResultsetRow;
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

    private List<Map<String, String>> resultSet;
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
            resultSet = new ArrayList<>();
            ResultSetMetaData mdata = rset.getMetaData();
            int len = mdata.getColumnCount();
            while(rset.next()){
                Map<String, String> resultMap = new LinkedHashMap<>(len);
                for (int j = 0; j < len; j++) {
                    String name = mdata.getColumnName(j + 1);
                    String value = rset.getString(name);
                    resultMap.put(name, value);
                }
                resultSet.add(resultMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 将查询的结果转换为实体类
     *
     * @param target
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> T conversionJavaBean(Class<T> target) {
        Map<String, String> resultMap = null;
        List<String> names = new ArrayList<>();
        T model = null;
        try {
            if (resultSet.isEmpty()) return null;
            resultMap = resultSet.get(0);
            model = target.newInstance();
            for (Field field : target.getDeclaredFields()) names.add(field.getName());
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
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

    /**
     * 将查询的结果转换为实体集合
     *
     * @param target
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T> List<T> conversionJavaList(Class<T> target) {
        List<T> models = new ArrayList<>();
        List<String> names = new ArrayList<>();
        try {
            if (resultSet.isEmpty()) return null;
            for (Field field : target.getDeclaredFields()) names.add(field.getName());
            for (Map<String, String> resultMap : resultSet) {
                T model = target.newInstance();
                for (Map.Entry<String, String> v : resultMap.entrySet()) {
                    String hump = TenguUtils.UnderlineToHump(v.getKey());
                    if (!names.contains(hump)) continue;                                 // 判断Model中是否含有hump字段
                    Field field = target.getDeclaredField(hump);
                    if (field.isAnnotationPresent(Ignore.class)) continue;               // 判断字段是否存在Ignore注解
                    field.setAccessible(true);
                    setValue(field, v.getValue(), model);
                }
                models.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return models;
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
            field.set(model, formatter.parse(v));
        }
        if (type.equals("java.lang.String")) {
            field.set(model, v);
        }
    }

}

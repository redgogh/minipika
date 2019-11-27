package com.tengu.db;

import com.alibaba.fastjson.JSONObject;
import com.tengu.annotation.Ignore;
import com.tengu.tools.TenguUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 自定义结果集，用于缓存结果从而使ResultSet提前关闭。
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/13 18:12
 * @since 1.8
 */
public class NativeResultSet implements NativeResultSetService {

    private List<Map<String, String>> resultSet;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public NativeResultSet() {
    }

    public NativeResultSet(ResultSet rset) {
        build(rset);
    }

    @Override
    public NativeResultSet build(ResultSet rset) {
        try {
            resultSet = new ArrayList<>();
            ResultSetMetaData mdata = rset.getMetaData();
            int len = mdata.getColumnCount();
            while (rset.next()) {
                Map<String, String> resultMap = new LinkedHashMap<>(len);
                for (int j = 0; j < len; j++) {
                    String name = mdata.getColumnLabel(j + 1);
                    String value = rset.getString(name);
                    resultMap.put(name, value);
                }
                resultSet.add(resultMap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset != null) rset.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    @Override
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

    @Override
    public <T> List<T> conversionJavaList(Class<T> target) {
        if (target.equals(String.class)) return conversionJavaStrings();
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

    /**
     * 将查询结果转换为String集合
     *
     * @return
     */
    private <T> List<T> conversionJavaStrings() {
        if (resultSet.isEmpty()) return null;
        List<String> strings = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                strings.add(v.getValue());
            }
        }
        return (List<T>) strings;
    }

    @Override
    public String toJSONString() {
        // 为了区分数组和单个对象
        if (resultSet.size() == 1) {
            return JSONObject.toJSONString(resultSet.get(0));
        }
        return JSONObject.toJSONString(resultSet);
    }

    /**
     * 添加对应的值
     *
     * @param field
     * @param v
     * @param model
     * @throws Exception
     */
    private void setValue(Field field, String v, Object model) throws Exception {
        if (field.getType().equals(String.class)) {
            field.set(model, v);
            return;
        }
        if (field.getType().equals(Date.class)) {
            field.set(model, formatter.parse(v));
            return;
        }
        if (field.getType().equals(Byte.class)) {
            field.set(model, Byte.valueOf(v));
            return;
        }
        if (field.getType().equals(Long.class)) {
            field.set(model, Long.valueOf(v));
            return;
        }
        if (field.getType().equals(Short.class)) {
            field.set(model, Short.valueOf(v));
            return;
        }
        if (field.getType().equals(Float.class)) {
            field.set(model, Float.valueOf(v));
            return;
        }
        if (field.getType().equals(Double.class)) {
            field.set(model, Double.valueOf(v));
            return;
        }
        if (field.getType().equals(Boolean.class)) {
            field.set(model, Boolean.valueOf(v));
            return;
        }
        if (field.getType().equals(Integer.class)) {
            field.set(model, Integer.valueOf(v));
            return;
        }
        if (field.getType().equals(BigDecimal.class)) {
            field.set(model, new BigDecimal(v));
            return;
        }
        if (field.getType().equals(BigInteger.class)) {
            field.set(model, new BigInteger(v));
            return;
        }
    }

}

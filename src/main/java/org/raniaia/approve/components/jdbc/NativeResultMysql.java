package org.raniaia.approve.components.jdbc;

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

import com.alibaba.fastjson.JSONObject;
import org.raniaia.approve.framework.provide.model.Ignore;
import org.raniaia.approve.framework.tools.Maps;
import org.raniaia.approve.framework.tools.ModelUtils;
import org.raniaia.approve.framework.tools.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings({"SpellCheckingInspection","unchecked"})
public class NativeResultMysql implements NativeResult {

    private String next;
    private int nextOffset = 0;
    private int hasNextOffset = 0;
    private List<String> hasNext;
    private List<Map<String, String>> resultSet;
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public NativeResultMysql() {
    }

    public NativeResultMysql(ResultSet input) {
        build(input);
    }

    @Override
    public NativeResult build(ResultSet rset) {
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
        if (resultSet.isEmpty()) return null;
        Map<String, String> resultMap;
        List<String> names = new ArrayList<>();
        T model = null;
        try {
            resultMap = resultSet.get(0);
            if (resultMap.isEmpty()) return null;
            Object v1 = base(target, String.valueOf(Maps.getFirstValue(resultMap)));
            if(v1 instanceof Exception) return null;
            if (v1 != null) return (T) v1;
            model = target.newInstance();
            for (Field field : target.getDeclaredFields()) names.add(field.getName());
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                String hump = ModelUtils.UnderlineToHump(v.getKey());
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
        try {
            if (target.equals(String.class)) return conversionJavaStringList();
            if (target.equals(Integer.class)) return conversionJavaIntegerList();
            if (target.equals(BigDecimal.class)) return conversionJavaBigDecimalList();
            if (target.equals(Long.class)) return conversionJavaLongList();
            if (target.equals(Double.class)) return conversionJavaDoubleList();
            if (target.equals(Short.class)) return conversionJavaShortList();
            if (target.equals(Float.class)) return conversionJavaFloatList();
            if (target.equals(Boolean.class)) return conversionJavaBooleanList();
            if (target.equals(Byte.class)) return conversionJavaByteList();
            if (target.equals(BigInteger.class)) return conversionJavaBigIntegerList();
            if (target.equals(Date.class)) return conversionJavaDateList();
            return conversionModelList(target);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换成Model List
     * @param target
     * @param <T>
     * @return
     */
    private <T> List<T> conversionModelList(Class<T> target) {
        List<T> models = new ArrayList<>();
        List<String> names = new ArrayList<>();
        try {
            for (Field field : target.getDeclaredFields()) names.add(field.getName());
            for (Map<String, String> resultMap : resultSet) {
                T model = target.newInstance();
                for (Map.Entry<String, String> v : resultMap.entrySet()) {
                    String hump = ModelUtils.UnderlineToHump(v.getKey());
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
     * @return1
     */
    private <T> List<T> conversionJavaStringList() {
        List<String> strings = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                strings.add(v.getValue());
            }
        }
        return (List<T>) strings;
    }

    /**
     * 将查询结果转换为Integer集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaIntegerList() {
        List<Integer> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(Integer.valueOf(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为Long集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaLongList() {
        List<Long> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(Long.valueOf(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为Short集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaShortList() {
        List<Short> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(Short.valueOf(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为Boolean集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaBooleanList() {
        List<Boolean> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(Boolean.valueOf(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为Double集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaDoubleList() {
        List<Double> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(Double.valueOf(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为Float集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaFloatList() {
        List<Float> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(Float.valueOf(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为Float集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaByteList() {
        List<Byte> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(Byte.valueOf(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为BigDecimal集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaBigDecimalList() {
        List<BigDecimal> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(new BigDecimal(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为BigInteger集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaBigIntegerList() {
        List<BigInteger> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(new BigInteger(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    /**
     * 将查询结果转换为BigInteger集合
     *
     * @return1
     */
    private <T> List<T> conversionJavaDateList() throws ParseException {
        List<Date> list = new ArrayList<>();
        for (Map<String, String> resultMap : resultSet) {
            for (Map.Entry<String, String> v : resultMap.entrySet()) {
                list.add(formatter.parse(v.getValue()));
            }
        }
        return (List<T>) list;
    }

    @Override
    public String toJSONString() {
        // 为了区分数组和单个对象
        if (resultSet.size() == 1) {
            return JSONObject.toJSONString(resultSet.get(0));
        }
        return JSONObject.toJSONString(resultSet);
    }

    @Override
    public void hasNext() {
        if (!resultSet.isEmpty()) {
            this.hasNext = new ArrayList<>(resultSet.get(this.hasNextOffset).values());
            this.hasNextOffset++;
            this.nextOffset = 0;
        }
    }

    @Override
    public String next() {
        if (hasNext == null) return null;
        String v = hasNext.get(this.nextOffset);
        this.nextOffset++;
        return v;
    }

    @Override
    public void reset() {
        this.next = null;
        this.hasNext = null;
        this.nextOffset = 0;
        this.hasNextOffset = 0;
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

    /**
     * 判断是不是基本数据类型或其他数据类型
     * @param target
     * @return
     */
    public Object base(Class<?> target, String value) throws ParseException {
        if (StringUtils.isEmpty(value)) return null;
        try {
            if (target.equals(String.class)) {
                return value;
            } else if (target.equals(Date.class)) {
                return formatter.parse(value);
            } else if (target.equals(Byte.class)) {
                return Byte.valueOf(value);
            } else if (target.equals(Long.class)) {
                return Long.valueOf(value);
            } else if (target.equals(Short.class)) {
                return Short.valueOf(value);
            } else if (target.equals(Float.class)) {
                return Float.valueOf(value);
            } else if (target.equals(Double.class)) {
                return Double.valueOf(value);
            } else if (target.equals(Boolean.class)) {
                return Boolean.valueOf(value);
            } else if (target.equals(Integer.class)) {
                return Integer.valueOf(value);
            } else if (target.equals(BigDecimal.class)) {
                return new BigDecimal(value);
            } else if (target.equals(BigInteger.class)) {
                return new BigInteger(value);
            }
        }catch (Exception e){
            return e;
        }
        return null;
    }

}

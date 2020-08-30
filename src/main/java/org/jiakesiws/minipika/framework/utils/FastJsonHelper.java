package org.jiakesiws.minipika.framework.utils;

/*
 * creates on 2020/5/11 19:47.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * 统一的json工具类，避免因fastjson序列化所导致的bug难以定位
 *
 * @author lts
 */
public class FastJsonHelper {

  /**
   * 对象转JsonString
   */
  public static String toJSONString(Object obj) {
    return JSON.toJSONString(obj,
            SerializerFeature.DisableCircularReferenceDetect, // 避免内存中循环引用导致解析错误
            SerializerFeature.WriteNullBooleanAsFalse,        // 如果遇到为null的boolean替换成false
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteMapNullValue
    );
  }

  /**
   * 将对象转换成JSONObject
   */
  public static JSONObject toJSONObject(Object obj) {
    return JSONObject.parseObject(toJSONString(obj));
  }

  /**
   * JSON字符串转对象
   */
  public static <T> T toJavaBean(String json, Class<T> clazz) {
    try {
      return JSON.parseObject(json, clazz);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * JSON字符串转JavaList
   */
  public static <E> List<E> toList(String input, Class<E> clazz) {
    JSONArray array = JSONArray.parseArray(input);
    return array.toJavaList(clazz);
  }

}

package org.jiakesimk.minipika.framework.util;

/*
 * Creates on 2020/3/21.
 */

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * 静态{@link Field}工具类。
 *
 * @author lts
 */
public class Fields {

  /**
   * 在{@param target}中获取{@link Field}。
   */
  public static Field[] getFields(Class<?> target) {
    return getFields(target, false);
  }

  /**
   * 在{@param target}中获取{@link Field}。
   */
  public static Field[] getFields(Class<?> target, boolean accessible) {
    Field[] fields = target.getFields();
    for (int i = 0; i < fields.length; i++) {
      fields[i].setAccessible(accessible);
    }
    return fields;
  }

  /**
   * 通过{@param annotations}获取{@link Field}。
   */
  public static Field[] getFields(Class<?> target,
                                  Class<? extends Annotation>[] annotations) {
    return getFields(target, annotations, false);
  }

  /**
   * 通过{@param annotations}获取{@link Field}。
   */
  public static Field[] getFields(Class<?> target,
                                  Class<? extends Annotation>[] annotations,
                                  boolean accessible) {
    return getFieldsByAnnotation(getFields(target, accessible), annotations);
  }

  /**
   * 在{@param target}中获取声明的{@link Field}。
   */
  public static Field[] getDeclaredFields(Class<?> target) {
    return getDeclaredFields(target, false);
  }

  /**
   * 在{@param target}中获取声明的{@link Field}。
   */
  public static Field[] getDeclaredFields(Class<?> target, boolean accessible) {
    Field[] fields = target.getDeclaredFields();
    for (int i = 0; i < fields.length; i++) {
      fields[i].setAccessible(accessible);
    }
    return fields;
  }

  /**
   * 获取类的所有成员，包括父类的
   *
   * @param classic    目标类
   * @param accessible 是否禁用安全检查
   * @return 所有成员
   */
  @SuppressWarnings({"ConstantConditions"})
  public static Field[] getDeclaredFieldsIncludeSuper(Class<?> classic, boolean accessible) {
    List<Class<?>> classes = ClassUtils.getSuperClasses(classic);
    List<Field> fields = Lists.newArrayList(getDeclaredFields(classic, accessible));
    for (Class<?> single : classes) {
      Field[] f = getDeclaredFields(single, true);
      if (f == null) continue;
      fields.addAll(Lists.ofList(f));
    }
    Field[] rf = new Field[fields.size()];
    return fields.toArray(rf);
  }

  /**
   * 获取类的所有成员，包括父类的
   *
   * @param classic     目标类
   * @param accessible  是否禁用安全检查
   * @param annotations 成员必须拥有annotations中的其中一个注解才可以返回
   * @return 所有成员
   */
  public static Field[] getDeclaredFieldsIncludeSuper(Class<?> classic, boolean accessible,
                                                      Class<? extends Annotation>[] annotations) {
    List<Field> haveAnnotationTheFields = Lists.newArrayList();
    Field[] fields = getDeclaredFieldsIncludeSuper(classic, accessible);
    for (Field field : fields) {
      for (Class<? extends Annotation> annotation : annotations) {
        if (field.isAnnotationPresent(annotation)) {
          haveAnnotationTheFields.add(field);
        }
      }
    }
    Field[] rf = new Field[haveAnnotationTheFields.size()];
    return haveAnnotationTheFields.toArray(rf);
  }

  /**
   * 通过{@param annotations}获取声明的{@link Field}。
   */
  public static Field[] getDeclaredFields(Class<?> target,
                                          Class<? extends Annotation>[] annotations) {
    return getFieldsByAnnotation(getDeclaredFields(target), annotations);
  }

  /**
   * 通过{@param annotations}获取声明的{@link Field}。
   */
  public static Field[] getDeclaredFields(Class<?> target,
                                          Class<? extends Annotation>[] annotations,
                                          boolean accessible) {
    return getFieldsByAnnotation(getDeclaredFields(target, accessible), annotations);
  }

  public static Field getDeclaredField(String name, Class<?> clazz) {
    return getDeclaredField(name, clazz, true);
  }

  public static Field getDeclaredField(String name, Class<?> clazz, boolean accessible) {
    try {
      Field field = clazz.getDeclaredField(name);
      field.setAccessible(accessible);
      return field;
    } catch (NoSuchFieldException e) {
      // e.printStackTrace();
      return null;
    }
  }

  /**
   * 通过{@param annotations}获取字段。
   * <p>
   * {@param accessible}是{@link Field ＃setAccessible}
   */
  public static Field[] getFieldsByAnnotation(Field[] fields,
                                              Class<? extends Annotation>[] annotations) {
    List<Field> fields0 = Lists.newLinkedList();
    for (Field field : fields) {
      for (Class<? extends Annotation> annotation : annotations) {
        if (field.isAnnotationPresent(annotation)) {
          fields0.add(field);
          break;
        }
      }
    }
    Field[] fieldArray = new Field[fields0.size()];
    fields0.toArray(fieldArray);
    return fieldArray;
  }

  /**
   * 设置{@code Filed}的值。
   */
  public static void set(Object instance, Object value, String name) {
    try {
      Field field = instance.getClass().getDeclaredField(name);
      field.setAccessible(true);
      field.set(instance, value);
    }catch (Exception e){
      throw new RuntimeException(e);
    }
  }

  /**
   * 获取成员属性值
   *
   * @param instance 实例对象
   * @param name     成员名称
   * @return 属性值
   */
  public static Object getValue(Object instance, String name) {
    try {
      Class<?> target = instance.getClass();
      Field field = target.getDeclaredField(name);
      field.setAccessible(true);
      return field.get(instance);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}

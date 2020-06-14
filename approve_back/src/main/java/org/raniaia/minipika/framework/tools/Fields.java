package org.jiakesiws.minipika.framework.tools;

/*
 * Creates on 2020/3/21.
 */

import org.jiakesiws.minipika.framework.tools.Lists;
import lombok.SneakyThrows;

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
   * 获取类的所有字段，包括父类的
   */
  @SuppressWarnings({"unchecked", "ConstantConditions"})
  public static Field[] getDeclaredFieldsIncludeSuper(Class<?> classic, boolean accessible) {
    List<Class<?>> classes = Classic.getSuperClasses(classic);
    List<Field> fields = Lists.newArrayList(getDeclaredFields(classic, accessible));
    for (Class<?> single : classes) {
      Field[] f = getDeclaredFields(single, true);
      if (f == null) continue;
      fields.addAll(Lists.asList(f));
    }
    Field[] rf = new Field[fields.size()];
    return fields.toArray(rf);
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
  @SneakyThrows
  public static void set(Object instance, Object value, Field field) {
    field.set(instance, value);
  }

}

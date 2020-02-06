package org.laniakeamly.poseidon.framework.loader;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * 类加载器
 * Copyright: Create by 2BKeyboard on 2019/12/12 13:50
 */
public class PoseidonClassLoader extends ClassLoader{

    public PoseidonClassLoader(){}

    /**
     * 根据字节码来加载类
     * @param name
     * @param classBytes
     * @return
     */
    public Class<?> findClassByBytes(String name,byte[] classBytes){
        return defineClass(name,classBytes,0,classBytes.length);
    }

    /**
     * 复制对象的所有属性并返回一个新的对象
     * @param target
     * @param src
     * @return
     */
    public Object getObject(Class<?> target,Object src){
        try {
            Object instance = target.getDeclaredConstructor().newInstance();
            Field[] fields = src.getClass().getDeclaredFields();
            for (Field oldField : fields) {
                String fieldName = oldField.getName();
                oldField.setAccessible(true);
                Field newInstanceField = instance.getClass().getDeclaredField(fieldName);
                newInstanceField.setAccessible(true);
                newInstanceField.set(instance,oldField.get(src));
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}

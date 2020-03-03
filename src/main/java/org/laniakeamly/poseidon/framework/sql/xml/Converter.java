package org.laniakeamly.poseidon.framework.sql.xml;

import javassist.*;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.loader.PoseidonClassLoader;
import org.laniakeamly.poseidon.framework.loader.PoseidonClassPool;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 编译解析后的Java代码
 * Copyright: Create by TianSheng on 2019/12/23 10:50
 */
public class Converter {

    private PoseidonClassPool pool = BeansManager.getBean("classPool");

    @SuppressWarnings("deprecation")
    public void conversion(PrecompiledMethod methodValue, Map<String, Object> parameter, String fullClassName) throws Exception {
        String methodString = process(methodValue.getMethodString(), parameter);
        CtClass ctClass = null;
        try {
            ctClass = pool.get(fullClassName);
        } catch (Exception e) {
            if(e instanceof NotFoundException) {
                ctClass = pool.makeClass(fullClassName);
            }else{
                e.printStackTrace();
            }
        }
        ctClass.defrost();
        CtMethod ctMethod = CtNewMethod.make(methodString, ctClass);
        ctClass.addMethod(ctMethod);
        PoseidonClassLoader classLoader = new PoseidonClassLoader(); // 类加载器
        Class<?> target = classLoader.findClassByBytes(fullClassName, ctClass.toBytecode());
        Object object = target.newInstance();
        Method iMethod = object.getClass().getDeclaredMethod(methodValue.getName(), Map.class, List.class);
        methodValue.setExecute(object);
        methodValue.setIMethod(iMethod);
    }

    /**
     * 处理java代码中的List
     * @param strValue
     * @param parameter
     * @return
     */
    private String process(String strValue, Map<String, Object> parameter) {
        String method = strValue;
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List) {
                List listValue = (List) value;
                if (!listValue.isEmpty()) {
                    value = listValue.get(0).getClass().getName();
                }
            }
            String valueName = "java.lang.Object";
            if (value != null) {
                valueName = value.getClass().getName();
            }
            method = method.replaceAll("#".concat(key).concat("#"), valueName);
        }
        return method;
    }

}

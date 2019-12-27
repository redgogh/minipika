package org.laniakeamly.poseidon.framework.sql;

import javassist.*;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.loader.PoseidonClassLoader;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 编译解析后的Java代码
 * Create by 2BKeyboard on 2019/12/23 10:50
 */
public class Converter {

    private ClassPool pool = BeansManager.getBean("classPool");

    public void conversion(PrecompiledMethod methodValue, Map<String,Object> parameter,String fullClassName) throws Exception {
        String methodString = process(methodValue.getMethodString(),parameter);
        CtClass ctClass = pool.makeClass(fullClassName);
        ctClass.defrost();
        CtMethod ctMethod = CtNewMethod.make(methodString, ctClass);
        ctClass.addMethod(ctMethod);
        PoseidonClassLoader classLoader = new PoseidonClassLoader(); // 类加载器
        Class<?> target = classLoader.findClassByBytes(fullClassName, ctClass.toBytecode());
        Object object = target.newInstance();
        Method iMethod = object.getClass().getDeclaredMethod(methodValue.getName(),Map.class,List.class);
        methodValue.setExecute(object);
        methodValue.setIMethod(iMethod);
    }

    private String process(String strValue,Map<String,Object> parameter){
        String method = strValue;
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof List){
                List listValue = (List) value;
                if(!listValue.isEmpty()){
                    value = listValue.get(0).getClass().getName();
                }
            }
            String valueName = "java.lang.Object";
            if(value != null){
                value.getClass().getName();
            }
            method = method.replaceAll("#".concat(key).concat("#"), valueName);
        }
        return method;
    }

}

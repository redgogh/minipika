package org.laniakeamly.poseidon.framework.compiler;

import javassist.*;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.loader.PoseidonClassLoader;
import org.laniakeamly.poseidon.framework.sql.ProvideConstant;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 预编译处理
 * Create by 2BKeyboard on 2019/12/19 11:18
 */
public class Precompiler {

    private ClassPool pool = BeansManager.getBean("classPool");

    /**
     * 加载一个类对象,只加载类信息不加载任何方法
     * @param target
     */
    public void loaderClass(PrecompiledClass target) {
        try {
            CtClass ctClass = pool.makeClass(target.getFullName());
            Class<?> instance = ctClass.toClass();
            target.setIClass(instance);
            // 表示自己已经被加载过了
            target.setLoad(true);
        } catch (CannotCompileException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析并编译方法
     * @param pc
     * @param mapperName
     * @param parameters
     */
    public PrecompiledMethod compilerMethod(PrecompiledClass pc, String mapperName, Map<String, Object> parameters) {
        try {
            PrecompiledMethod pm = pc.getPrecompiledMethod(mapperName);
            if (true) return pm; // 如果已经编译过了直接返回不再进行下面的操作
            String methodString = processStringMethod(pm.toString(), parameters);
            CtClass ctClass = pool.get(pc.getFullName());
            ctClass.defrost();
            CtMethod ctMethod = CtNewMethod.make(methodString, ctClass);
            ctClass.addMethod(ctMethod);
            PoseidonClassLoader classLoader = new PoseidonClassLoader(); // 类加载器
            Class<?> target = classLoader.findClassByBytes(pc.getFullName(), ctClass.toBytecode());
            Object object = target.newInstance();
            object = classLoader.getObject(target, object);
            pm.setExecute(object);
            pm.setIMethod(object.getClass().getDeclaredMethod(pm.getName(), Map.class, List.class));
            pm.setLoad(true);
            return pm;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对字符串最后一次处理,将里面的需要替换的字符替换掉,让Java能够识别出来
     * 比如说: 强转字符标识 (#name#) 替换成 (java.lang.String)
     *
     * @param str           方法字符串
     * @param parameters    参数
     */
    private String processStringMethod(String str, Map<String, Object> parameters) {
        return null;
    }

}

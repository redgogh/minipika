package org.laniakeamly.poseidon.framework.compiler;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 预编译处理
 * Create by 2BKeyboard on 2019/12/19 11:18
 */
public class Precompiler {

    private ClassPool pool = ClassPool.getDefault();

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
    public void compilerMethod(PrecompiledClass pc, String mapperName, Map<String, Object> parameters) {
        PrecompiledMethod pm = pc.getPrecompiledMethod(mapperName);
        processStringMethod(pm.toString(), parameters);
    }

    /**
     * 对字符串最后一次处理,将里面的需要替换的字符替换掉,让Java能够识别出来
     * 比如说: 强转字符标识 (#name#) 替换成 (java.lang.String)
     *
     * @param str           方法字符串
     * @param parameters    参数
     */
    private void processStringMethod(String str, Map<String, Object> parameters) {
        List<String> names = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(.*?)#");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            names.add(matcher.group(1));
        }
        str = str.replaceAll("\\(#null#\\) map.get\\(\"null\"\\)", "null");
        for (String name : names) {
            if (!name.equals("null")) {
                String trueName = parameters.get(name).getClass().getName();
                str = str.replaceAll(("#" + name + "#"), trueName);
            }
        }
        System.out.println(str);
    }

}

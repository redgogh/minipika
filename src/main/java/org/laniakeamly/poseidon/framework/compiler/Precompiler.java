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
            if(pm.isLoad()) return pm; // 如果已经编译过了直接返回不再进行下面的操作
            String methodString = processStringMethod(pm.toString(), parameters);
            CtClass ctClass = pool.get(pc.getFullName());
            ctClass.defrost();
            CtMethod ctMethod = CtNewMethod.make(methodString,ctClass);
            ctClass.addMethod(ctMethod);
            PoseidonClassLoader classLoader = PoseidonClassLoader.getClassLoader(); // 类加载器
            // todo BUG: 类名重复定义
            Class<?> target = classLoader.findClassByBytes(pc.getFullName(),ctClass.toBytecode());
            Object object = target.newInstance();
            object = classLoader.getObject(target,object);
            pm.setExecute(object);
            pm.setIMethod(object.getClass().getDeclaredMethod(pm.getName(),Map.class,List.class));
            pm.setLoad(true);
            return pm;
        }catch (Exception e){
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
        List<String> names = new ArrayList<>();
        Pattern pattern = Pattern.compile("#(.*?)#");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            names.add(matcher.group(1));
        }
        // 处理null
        str = str.replaceAll("\\(#null#\\) map.get\\(\"null\"\\)", "null");
        for (String name : names) {
            if (!name.equals("null")) {
                String trueName = parameters.get(name).getClass().getName();
                str = str.replaceAll(("#" + name + "#"), trueName);
            }
        }
        // 解析所有变量所在的地方
        boolean isString = false;
        char[] charArray = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        List<Position> positions = new ArrayList<>();
        int endPos = 0;
        int startPos = 0;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            if (c == '"') {
                if (!isString) {
                    isString = true;
                    startPos = i;
                } else {
                    endPos = i;
                    isString = false;
                }
            } else {
                if (isString) {
                    builder.append(c);
                }
            }

            if (c == ';') {

                if (isString == false && builder.length() != 0) {
                    positions.add(new Position(builder.toString(), startPos, endPos, i));
                    StringUtils.clear(builder);
                }
            }
        }
        Collections.reverse(positions);
        builder = new StringBuilder(str);
        for (Position position : positions) {
            List<String> params = new ArrayList<>();
            pattern = Pattern.compile("\\{\\{(.*?)}}");
            matcher = pattern.matcher(position.str);
            int count = 0;
            while (matcher.find()) {
                params.add(matcher.group(1));
                count++;
            }
            if (count <= 0) {
                continue;
            }
            String addParamCode = position.addParams(params);
            String replaced = position.str.replaceAll("\\{\\{(.*?)}}", "?");
            builder.replace(position.semicolonPos, position.semicolonPos + 1, ";".concat(addParamCode));
            builder.replace(position.startPos, position.endPos + 1, "\"".concat(replaced).concat("\""));
        }
        return builder.toString();
    }

    private class Position {
        String str;
        int startPos;
        int endPos;
        int semicolonPos;

        public Position(String str, int startPos, int endPos, int semicolonPos) {
            this.str = str;
            this.startPos = startPos;
            this.endPos = endPos;
            this.semicolonPos = semicolonPos;
        }

        public String addParams(List<String> args) {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(StringUtils.format(ProvideConstant.SQL_PARAMS_SET + ".add(map.get(\"{}\"));", arg));
            }
            return builder.toString();
        }

    }

}

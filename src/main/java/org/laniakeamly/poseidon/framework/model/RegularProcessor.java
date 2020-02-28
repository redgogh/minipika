package org.laniakeamly.poseidon.framework.model;

import javassist.*;
import lombok.Getter;
import lombok.Setter;
import org.laniakeamly.poseidon.framework.annotation.model.Model;
import org.laniakeamly.poseidon.framework.annotation.model.Regular;
import org.laniakeamly.poseidon.framework.loader.PoseidonClassPool;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Regular注解处理器
 *
 * 处理过程是先获取到注解然后再根据注解生成{@link RegularProperties}属性
 * 最后根据生成的{@link RegularProperties}对类的字节码做动态修改来达到最终效果。
 *
 * English:
 *
 * Regular annotation processor.
 *
 * this processor first get {@link Regular} then create
 * {@link RegularProperties} properties.
 *
 * end according to {@link RegularProperties} modify class bytecode.
 *
 * Copyright Create by TianSheng on 2020/2/8 0:09
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class RegularProcessor {

    private List<RegularProperties> properties;
    private ClassLoader loader = getClass().getClassLoader();
    private final ClassPool classPool = new PoseidonClassPool(true);
    private final String superClasspath = "org.laniakeamly.poseidon.framework.model.AbstractModel";
    private final String methodJavaCode = "" +
            "{" +
            "if(" +
            "!org.laniakeamly.poseidon.framework.tools." +
            "RegularUtils.getInstanceSave().matches($1," +
            "org.laniakeamly.poseidon.framework.config.GlobalConfig.getConfig().getRegular(\"{}\"))" +
            "){super.canSave=false;}" +
            "}";

    public RegularProcessor(String[] packages) throws Exception {
        properties = getRegularProperties(packages);
    }

    /**
     * 通过修改字节码重写Setter方法
     */
    public void modifySetter() {
        if (properties == null) return;
        try {
            for (RegularProperties property : properties) {
                CtClass clazz = property.getCtClass();
                clazz.defrost();
                // 修改Setter方法
                Map<String, Regular> fields = property.getFields();
                for (Map.Entry<String, Regular> entry : fields.entrySet()) {
                    String entryKey = entry.getKey();
                    String methodName = "set".concat(StringUtils.UpperCase(entryKey, 1));
                    CtMethod ctMethod = clazz.getDeclaredMethod(methodName);
                    ctMethod.setBody(StringUtils.format(methodJavaCode, entry.getValue().value(), entryKey));
                    ctMethod.insertAfter(StringUtils.format("{} = $1;", entryKey));
                }
                // 重新编译并加载新的Model类
                String classpath = clazz.getName();
                String filedir = loader.getResource(".").toString().substring(6);
                clazz.writeFile(filedir);
                loader.loadClass(classpath);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Getter
    @Setter
    class RegularProperties {

        /**
         * model class path.
         */
        private String classpath;

        private CtClass ctClass;

        /**
         * fields and {@link Regular} annotations
         */
        private Map<String, Regular> fields;

        public RegularProperties() {
        }

        public RegularProperties(String classpath, CtClass ctClass) {
            this.ctClass = ctClass;
            this.classpath = classpath;
        }

    }

    /**
     * 获取扫描到的Regular属性
     * @throws ClassNotFoundException
     */
    private List<RegularProperties> getRegularProperties(String[] packages) throws Exception {
        List<RegularProperties> regularPropertiesList = new ArrayList<>();
        for (String aPackage : packages) {
            CtClass[] ctClasses = ((PoseidonClassPool) classPool).getCtClassArray(aPackage);
            for (CtClass aClass : ctClasses) {
                if (aClass.getAnnotation(Model.class) != null) {
                    String filedir = loader.getResource(".").toString().substring(6);
                    CtClass superClass = classPool.get(superClasspath);
                    // 给Model类添加一个父类
                    aClass.setSuperclass(superClass);
                    aClass.writeFile(filedir);
                }
                RegularProperties properties = null;
                Map<String, Regular> hashMap = null;
                CtField[] fields = aClass.getDeclaredFields();
                for (CtField field : fields) {
                    Object[] annotations = field.getAnnotations();
                    for (Object annotation : annotations) {
                        if (annotation instanceof Regular) {
                            Regular regular = (Regular) annotation;
                            if (hashMap == null) hashMap = new HashMap<>();
                            hashMap.put(field.getName(), regular);
                        }
                    }
                }
                if (hashMap != null) {
                    String classname = aClass.getPackageName().concat(aClass.getName());
                    properties = new RegularProperties(classname, aClass);
                    properties.setFields(hashMap);
                    regularPropertiesList.add(properties);
                }
            }
        }

        return regularPropertiesList.isEmpty() ? null : regularPropertiesList;
    }

}

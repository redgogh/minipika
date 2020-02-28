package org.laniakeamly.poseidon.framework.loader;

import com.sun.org.apache.bcel.internal.classfile.LocalVariable;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.LoaderClassPath;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright by TianSheng on 2020/2/8 13:12
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class PoseidonClassPool extends ClassPool {

    public PoseidonClassPool() {
    }

    public PoseidonClassPool(boolean useDefaultPath) {
        super(useDefaultPath);
    }

    public PoseidonClassPool(ClassPool parent) {
        super(parent);
    }

    /**
     * 将某个包下的Class转换成CtClass
     * @param packageName
     * @return
     */
    public CtClass[] getCtClassArray(String packageName) {
        // appendClassPath(new LoaderClassPath(this.getClass().getClassLoader()));
        List<String> classpathList = new ArrayList<>();
        getFile(packageName, classpathList);
        CtClass[] ctClasses = new CtClass[classpathList.size()];
        for (int i = 0, len = classpathList.size(); i < len; i++) {
            ctClasses[i] = createCtClass(classpathList.get(i), true);
        }
        return ctClasses;
    }

    private void getFile(String basePackage, List<String> fileList) {
        URL url = getClass().getResource("/" + basePackage.replaceAll("\\.", "/"));
        if (url == null) return;
        File dir = new File(url.getFile());
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                getFile(basePackage + "." + file.getName(), fileList);
            } else {
                String classes = basePackage + "." + file.getName();
                if ("class".equals(classes.substring(classes.lastIndexOf(".") + 1))) {
                    classes = classes.replace(".class", "");
                    fileList.add(classes);
                }
            }
        }
    }

    /**
     * 获取方法参数名
     * @return
     */
    public Map<String, Object> getMethodParametersName(Class<?> clazz, String method) {
        Map<String, Object> names = new HashMap<>();
        try {
            CtClass ctClass = get(clazz.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(method);
            MethodInfo info = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = info.getCodeAttribute();
            String[] paramsNames = new String[ctMethod.getParameterTypes().length];
            LocalVariableAttribute attribute =
                    (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attribute != null) {
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                for (int i = 0; i < paramsNames.length; i++){
                    paramsNames[i] = attribute.variableName(i + pos);
                }
            }
            System.out.println();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return names;
    }

}

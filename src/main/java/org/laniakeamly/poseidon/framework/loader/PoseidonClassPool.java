package org.laniakeamly.poseidon.framework.loader;

import com.sun.org.apache.bcel.internal.classfile.LocalVariable;
import javassist.*;
import javassist.bytecode.ByteArray;
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
     * 获取对象方法参数名
     * @return
     */
    public String[] getParamName(String classname, String methodname) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(classname);
            CtMethod cm = cc.getDeclaredMethod(methodname);
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            String[] paramNames = new String[cm.getParameterTypes().length];
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
                for (int i = 0; i < paramNames.length; i++) {
                    paramNames[i] = attr.variableName(i + pos);
                }
                return paramNames;
            }
        } catch (Exception e) {
            System.out.println("getMethodVariableName fail " + e);
        }
        return null;
    }


}

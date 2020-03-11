package org.raniaia.poseidon.framework.loader;

import javassist.*;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

}

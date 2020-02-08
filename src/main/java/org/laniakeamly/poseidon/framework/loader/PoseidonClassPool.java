package org.laniakeamly.poseidon.framework.loader;

import javassist.ClassPool;
import javassist.CtClass;

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
     * This is the method to get the class under the specified package.
     * The obtained class will be converted into a CtClass array.
     *
     * @param packageName   get the contents of this package.
     * @return
     */
    public CtClass[] getCtClassArray(String packageName) {
        List<String> classpathList = new ArrayList<>();
        getFile(packageName,classpathList);
        CtClass[] ctClasses = new CtClass[classpathList.size()];
        for (int i=0,len=classpathList.size(); i<len; i++) {
            ctClasses[i] = createCtClass(classpathList.get(i),true);
        }
        return ctClasses;
    }

    /**
     * get class path in specify package.
     * this method is recursive method.
     * @param basePackage   scanner this path.
     * @param fileList      List<String>
     */
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

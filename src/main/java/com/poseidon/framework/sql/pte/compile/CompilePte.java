package com.poseidon.framework.sql.pte.compile;

import javassist.ClassPool;
import javassist.CtClass;
import sun.java2d.loops.FillRect;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 编译pte文件
 * Create by 2BKeyboard on 2019/12/12 13:55
 */
public class CompilePte {

    private ClassPool pool = ClassPool.getDefault();

    private String makeClassPrefix = "com.poseidon.framework.$space.$";

    public static void main(String[] args) throws IOException {

    }

    public void compile(String className) {
        className = getClassName(className);
        CtClass _class = pool.makeClass(className);

    }

    public String getClassName(String name) {
        return makeClassPrefix.concat(name);
    }

}

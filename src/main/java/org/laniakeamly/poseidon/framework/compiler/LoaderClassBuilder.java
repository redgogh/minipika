package org.laniakeamly.poseidon.framework.compiler;

import org.laniakeamly.poseidon.framework.sql.token.ClassBuilder;

import java.util.List;

/**
 * 加载ClassBuilder对象
 * Create by 2BKeyboard on 2019/12/15 16:29
 */
public class LoaderClassBuilder {

    public void put(List<ClassBuilder> classBuilders){

        for (ClassBuilder classBuilder : classBuilders) {
            classBuilder.methodToClassBody();
            System.out.println(classBuilder.toString());

        }

    }

}

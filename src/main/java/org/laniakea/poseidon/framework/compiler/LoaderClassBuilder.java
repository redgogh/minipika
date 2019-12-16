package org.laniakea.poseidon.framework.compiler;

import org.laniakea.poseidon.framework.sql.builder.ClassBuilder;

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

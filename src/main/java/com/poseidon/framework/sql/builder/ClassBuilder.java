package com.poseidon.framework.sql.builder;

import com.poseidon.framework.tools.NewlineBuilder;
import com.poseidon.framework.tools.StringNewline;
import com.poseidon.framework.tools.StringUtils;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/13 15:14
 */
public class ClassBuilder extends StringNewline implements CodeBuilderFactory{

    private List<MethodBuilder> methods;
    private List<NewlineBuilder> varibale;

    public ClassBuilder() {
    }

    public ClassBuilder(int size) {
        super(size);
    }

    public ClassBuilder(int valueCapacity, int lineCapacity) {
        super(valueCapacity, lineCapacity);
    }

    public ClassBuilder(String str) {
        super(str);
    }

    public ClassBuilder(char[] charArray) {
        super(charArray);
    }


    @Override
    public ClassBuilder createObject(String name, String type){
        append(type).append(" ").append(name).append(" = ").append(type).append("();\n");
        return this;
    }

    @Override
    public ClassBuilder transfer(String variable, String method) {
        return transfer(variable, method,null);
    }

    @Override
    public ClassBuilder transfer(String variable, String method, String... args) {
        String content = "(";
        if(args != null){
            for(String arg : args){
                content.concat(arg).concat(",");
            }
            content = StringUtils.deleteLastString(content);
        }
        content.concat(");\n");
        append(variable).append(".").append(method).append(content);
        return null;
    }

    @Override
    public ClassBuilder addMethod(MethodBuilder methodBuilder) {
        methods.add(methodBuilder);
        return this;
    }
}
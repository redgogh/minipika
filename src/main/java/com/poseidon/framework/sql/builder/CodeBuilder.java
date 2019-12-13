package com.poseidon.framework.sql.builder;

import com.poseidon.framework.tools.StringNewline;
import com.poseidon.framework.tools.StringUtils;
import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/13 15:14
 */
public class CodeBuilder extends StringNewline implements CodeBuilderFactory{

    private List<CodeBuilder> methods;

    public CodeBuilder() {
    }

    public CodeBuilder(int size) {
        super(size);
    }

    public CodeBuilder(int valueCapacity, int lineCapacity) {
        super(valueCapacity, lineCapacity);
    }

    public CodeBuilder(String str) {
        super(str);
    }

    public CodeBuilder(char[] charArray) {
        super(charArray);
    }

    @Override
    public CodeBuilder createMethod(String modify, String args, String _return) {
        CodeBuilder method = new CodeBuilder(50);
        String returnStr = "";
        if(_return == null){
            modify = StringUtils.format(modify,"void");
        }else{
            modify = StringUtils.format(modify,_return);
            returnStr = "return null;";
        }
        modify = modify.concat("(").concat(args).concat("){\n");
        method.append(modify);
        method.append(returnStr);
        method.append("}\n");
        return null;
    }

    @Override
    public CodeBuilder createObject(String name,String type){
        append(type).append(" ").append(name).append(" = ").append(type).append("();\n");
        return this;
    }

    @Override
    public CodeBuilder transfer(String variable, String method) {
        return transfer(variable, method,null);
    }

    @Override
    public CodeBuilder transfer(String variable, String method, String... args) {
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

    public static void main(String[] args) {
        CodeBuilder builder = new CodeBuilder();
        builder.createObject("sql","StringBuilder");
        System.out.println(builder.toString());
    }

}
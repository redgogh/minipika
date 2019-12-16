package org.maitreya.poseidon.framework.sql.builder;

import org.maitreya.poseidon.framework.tools.NewlineBuilder;
import org.maitreya.poseidon.framework.tools.StringNewline;
import org.maitreya.poseidon.framework.tools.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Create by 2BKeyboard on 2019/12/13 23:57
 */
public class MethodBuilder extends StringNewline implements MethodBuilderFacotry {

    private int next = 1;

    @Getter
    @Setter
    private String args;

    public MethodBuilder() {
    }

    public MethodBuilder(int size) {
        super(size);
    }

    public MethodBuilder(int valueCapacity, int lineCapacity) {
        super(valueCapacity, lineCapacity);
    }

    public MethodBuilder(String str) {
        super(str);
    }

    public MethodBuilder(char[] charArray) {
        super(charArray);
    }

    @Override
    public MethodBuilder createMethod(String modify,String _return,String... args) {
        String returnStr = "";
        modify = StringUtils.format(modify, args);
        if (_return != null) {
            returnStr = "\treturn ".concat(_return).concat(";\n");
        }
        // 这边需要格式化的是参数
        modify = modify.concat("({}").concat(")\n{\n");
        append(modify);
        append(returnStr);
        append("}\n");
        return this;
    }

    /**
     * 添加一行代码
     *
     * @param value 需要添加的代码
     * @return this
     */
    public MethodBuilder insert(String value) {
        insertLine(next, "\t".concat(value));
        next++;
        return this;
    }

    public MethodBuilder insert(NewlineBuilder value){
        while(value.hasNext()){
            insert(value.next());
        }
        return this;
    }

    /**
     * 给方法添加上参数
     * @param args
     * @return
     */
    public String toString(String args) {
        return StringUtils.format(super.toString(),args);
    }
}

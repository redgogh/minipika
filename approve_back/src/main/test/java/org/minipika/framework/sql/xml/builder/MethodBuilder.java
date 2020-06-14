package framework.sql.xml.builder;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



import org.jiakesimk.minipika.framework.tools.NewlineBuilder;
import lombok.Getter;
import lombok.Setter;
import org.jiakesimk.minipika.framework.tools.StringUtils;

/**
 * Copyright: Create by tiansheng on 2019/12/13 23:57
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

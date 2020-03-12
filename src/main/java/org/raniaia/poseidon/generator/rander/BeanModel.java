package org.raniaia.poseidon.generator.rander;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * Java bean渲染数据模型
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@Getter
@Setter
public class BeanModel {

    private String package_name;
    private String class_name;
    private List<Attribute> attributes;

    public static Attribute createAttribute(String name, String type) {
        return new Attribute(name, type);
    }

    @Getter
    static class Attribute {
        String name;
        String type;

        public Attribute() {
        }

        public Attribute(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }

}

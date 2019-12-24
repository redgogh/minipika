package org.laniakeamly.poseidon.framework.sql;

import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.Map;

/**
 * 编译解析后的Java代码
 * Create by 2BKeyboard on 2019/12/23 10:50
 */
public class Converter {

    public void conversion(PrecompiledMethod methodValue, Map<String,Object> parameter){

        String method = methodValue.getMethodString();
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {

        }
        System.out.println(method);

    }

}

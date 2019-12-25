package org.laniakeamly.poseidon.framework.sql;

import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.List;
import java.util.Map;

/**
 * 编译解析后的Java代码
 * Create by 2BKeyboard on 2019/12/23 10:50
 */
public class Converter {

    public void conversion(PrecompiledMethod methodValue, Map<String,Object> parameter){
        String method = methodValue.getMethodString();
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof List){
                List listValue = (List) value;
                if(!listValue.isEmpty()){
                    value = listValue.get(0).getClass().getName();
                }
            }
            method = method.replaceAll("#".concat(key).concat("#"),
                    value.getClass().getName());
        }
    }

}

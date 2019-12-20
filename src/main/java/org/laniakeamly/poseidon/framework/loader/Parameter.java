package org.laniakeamly.poseidon.framework.loader;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数
 * Create by 2BKeyboard on 2019/12/20 14:15
 */
public class Parameter {

    List<Object> params = new ArrayList<>();

    public void add(Object obj){
        params.add(obj);
    }

    public Object[] toArray(){
        return params.toArray();
    }

}

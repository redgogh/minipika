package com.poseidon.framework.cache;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/6 16:24
 */
public class CacheKey {

    @Getter
    @Setter
    private String key;

    @Getter
    @Setter
    private List<String> tables;

    public boolean exists(String table){
        return table.contains(table);
    }

}

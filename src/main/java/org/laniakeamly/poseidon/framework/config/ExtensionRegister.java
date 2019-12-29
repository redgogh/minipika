package org.laniakeamly.poseidon.framework.config;

import lombok.Getter;
import org.laniakeamly.poseidon.extension.ConnectionPool;

/**
 * Create by 2BKeyboard on 2019/12/29 14:35
 */
public class ExtensionRegister {

    @Getter
    private ConnectionPool connectionPool = new org.laniakeamly.poseidon.framework.pool.ConnectionPool();

    public <V> void register(V v){
        if(v instanceof ConnectionPool){
            connectionPool = (ConnectionPool) v;
        }
    }

}

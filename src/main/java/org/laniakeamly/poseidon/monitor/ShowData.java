package org.laniakeamly.poseidon.monitor;

import lombok.Getter;
import lombok.Setter;

/**
 * 展示数据
 * Create by 2BKeyboard on 2019/12/31 16:50
 */
@Getter
@Setter
public class ShowData {

    /**
     * 当前正在使用连接数
     */
    private int currentConnection = 0;

    /**
     * 当前连接池剩余连接数
     */
    private int remainderConnection = 0;

}

package org.laniakeamly.poseidon.framework.monitor;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 本地数据库 | Local database
 *
 * Copyright by TianSheng on 2020/3/3 16:08
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public final class LocalDatabase {

    private HashMap<String, Entry> entries = new HashMap<>();

    /**
     * 数据存储对象 | Data save object
     */
    class Entry {
    }

}

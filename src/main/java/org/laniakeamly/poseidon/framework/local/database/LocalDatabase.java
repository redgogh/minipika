package org.laniakeamly.poseidon.framework.local.database;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.laniakeamly.poseidon.framework.annotation.LocalModel;
import org.laniakeamly.poseidon.framework.tools.Arrays;
import org.laniakeamly.poseidon.framework.tools.Lists;
import org.laniakeamly.poseidon.framework.tools.Maps;
import org.laniakeamly.poseidon.framework.tools.ReflectUtils;

import java.util.List;
import java.util.Map;

/**
 *
 * 本地数据库，为监控器而开发的数据库。本地数据库的作用是保存监控器所监控到的数据信息，
 * 方便开发者对数据的导出。这个本地数据库是基于Hash表结构实现的。
 *
 * 如果开发者不想导出数据或者其他操作可以设置监控器数据过期时间，或者是设置数据到达多少大小清空。
 *
 * 所有数据都存放在{@link Entry}对象中，{@code Entry}是一个链表，可以理解为它是一张数据库的表。
 * 它里面包这张表的所有数据
 *
 * Local database, this database was developed for the monitor. it
 * can help developer save monitoring data then export to database or cache. if
 * developer not want export you can set data expired time.
 *
 * This local database is implemented based on the hash table structure.
 *
 * All of data save in {@link Entry}, you can put {@code Entry} understand for a table.
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
@SuppressWarnings({"unchecked"})
public class LocalDatabase
        implements Database {

    /**
     * 哈希桶 | hash bucket
     */
    EntryNode[] table;

    /**
     * 未使用数组空间
     */
    int UNUSED_SPACE;

    /**
     * 负载因子
     */
    final float DEFAULT_LOAD_FACTOR = 0.75F;

    // todo 测试使用
    public static Map<String, Integer> map = Maps.newHashMap();

    @Getter
    @Setter
    class Entry {
        List<Node> data;
        Node model; // 字段模型
        Entry next;
        Entry last;
        String tableName;
        Map<String, Integer> columnIndex;

        // 创建Entry | create Entry
        Entry(String tableName, String... columns) {
            Node temp = null;
            this.tableName = tableName;
            columnIndex = Maps.newHashMap();
            for (int i = 0; i < columns.length; i++) {
                String column = columns[i];
                columnIndex.put(column, i);
                if (model == null) {
                    model = new Node(column);
                    temp = model;
                    continue;
                }
                if (temp.next == null) {
                    temp.next = new Node(column);
                    temp = temp.next;
                }
            }
        }

        /**
         * {@code Entry} equals method.
         *
         * @param entry {@link Entry}
         * @return {@link Boolean}
         */
        boolean equals(Entry entry) {
            return this == entry || this.tableName.equals(entry.tableName);
        }

        /**
         * Append a new {@code Entry} instance.
         *
         * @param entry the Entry.
         */
        void append(Entry entry) {
            if (next == null) {
                next = entry;
                last = entry;
                return;
            }
            last.next = entry;
            last = last.next;
        }

        /**
         * Insert a piece for data.
         *
         * @param map data object.
         */
        void insert(Map<String, Object> map) {
            Node node = Node.copyof(model);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Integer index = columnIndex.get(entry.getKey());
                if (index == null) continue;
                Node temp = node;
                for (int i = 0; i < index; i++) {
                    temp = node.next;
                }
                temp.setValue(entry.getValue());
            }
            if (data == null) {
                data = Lists.newLinkedList();
                data.add(node);
            } else {
                data.add(node);
            }
            System.out.println();
        }

        List<Node> getEntryData() {
            return data;
        }

    }

    @Getter
    @Setter
    class EntryNode {
        int len;
        Entry data;

        EntryNode() {
        }

        EntryNode(Entry data) {
            this.data = data;
        }

        void append(Entry value) {
            data.append(value);
        }

    }

    @Getter
    @Setter
    static class Node {
        Node next;
        String key;
        Object value;

        Node() {
        }

        Node(String key) {
            this.key = key;
        }

        /**
         * 深拷贝
         * @param n 被拷贝的对象
         * @return
         */
        static Node copyof(Node n) {
            return JSONObject.parseObject(JSON.toJSONString(n)).toJavaObject(Node.class);
        }

    }

    public LocalDatabase() {
        this(16);
    }

    public LocalDatabase(int size) {
        table = new EntryNode[size];
    }

    /**
     * 添加新的{@link Entry}对象
     * put new {@link Entry} instance by hash.
     *
     * @param hash hash for key
     * @param value the value
     */
    private void putVal(int hash, Entry value) {
        if (loadFactor()) expansion();
        EntryNode entryNode = table[hash];
        if (entryNode == null) {
            EntryNode node = new EntryNode(value);
            Arrays.append(table, node, hash);
            this.UNUSED_SPACE++;
        } else {
            entryNode.append(value);
        }
        Integer temp = map.get(String.valueOf(hash));
        if (temp != null) {
            map.put(String.valueOf(hash), temp + 1);
        } else {
            map.put(String.valueOf(hash), 0);
        }
    }

    /**
     * get value for {@link Entry}.
     * @param key the key
     * @return {@code Entry} object.
     */
    public Entry getVal(String key) {
        Entry entry = table[hash(key)].data;
        if (key.equals(entry.tableName)) {
            return entry;
        }
        Entry temp = entry;
        while ((temp = temp.next) != null) {
            if (temp.tableName.equals(key)) {
                return temp;
            }
        }
        return null;
    }

    public int hash(String key) {
        int h;
        int hashCode = ((key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)) % table.length;
        return hashCode < 0 ? hashCode * -1 : hashCode;
    }

    /**
     * 获取当前负载因子，判断数组是否需要扩容
     */
    private boolean loadFactor() {
        return ((float) UNUSED_SPACE / table.length) > DEFAULT_LOAD_FACTOR;
    }

    /**
     * 数组扩容
     */
    private void expansion() {
        table = (EntryNode[]) Arrays.expansion(table, 16, EntryNode.class);
    }

    /**
     * implements for {@link Database#createTable}
     */
    @Override
    public void createTable(String tableName, String... columns) {
        Entry entry = new Entry(tableName, columns);
        putVal(hash(tableName), entry);
    }

    /**
     * implements for {@link Database#queryForList}
     */
    @Override
    public List<?> queryForList(Class<? extends AbstractLocalModel> c) {
        try {
            LocalModel model = c.getDeclaredAnnotation(LocalModel.class);
            Entry entry = getVal(model.value());
            List<Node> nodes = entry.getEntryData();
            List<AbstractLocalModel> result = Lists.newLinkedList();
            for (Node node : nodes) {
                Node n = node;
                AbstractLocalModel abstractLocalModel = c.newInstance();
                ReflectUtils.setMemberValue(abstractLocalModel, n.key, n.value);
                while ((n = n.next) != null) {
                    ReflectUtils.setMemberValue(abstractLocalModel, n.key, n.value);
                }
                result.add(abstractLocalModel);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(String sql, Object... args) {
    }

    /**
     * implements for {@link Database#insert}
     */
    @Override
    public void insert(Object o) {
        String table = o.getClass().getDeclaredAnnotation(LocalModel.class).value();
        Map<String, Object> map = JSONObject.parseObject(JSON.toJSONString(o));
        Entry entry = getVal(table);
        entry.insert(map);
    }

}

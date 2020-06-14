package org.jiakesiws.minipika.framework.tools;

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

/*
 * Creates on 2020/3/11.
 */

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.*;

/**
 * Static {@link List} tools class.
 *
 * @author TianSheng
 */
public final class Lists {

    /**
     * 创建一个可变的 {@code ArrayList}
     * @return 空的 {@code ArrayList} 实例
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 创建一个可变的 {@code ArrayList}
     * 复制一个{@link List}中的数据到新的{@code List}中
     *
     * @return 新的List且带有传入List数据的实例
     */
    public static <E> ArrayList<E> newArrayList(Collection<? extends E> collection) {
        return new ArrayList<>(collection);
    }

    /**
     * 创建一个可变的 {@code ArrayList}
     * 复制数组中的内容到新创建的{@code ArrayList}中
     *
     * @return 新的List且带有传入List数据的实例
     */
    public static <E> ArrayList newArrayList(E[] es){
        return new ArrayList<>(java.util.Arrays.asList(es));
    }

    /**
     * 创建一个可变的 {@code LinkedList}
     * @return 空的 {@code LinkedList} 实例
     */
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    /**
     * 创建一个可变的 {@code LinkedList}
     * 复制一个{@link List}中的数据到新的{@code LinkedList}中
     *
     * @return 新的List且带有传入List数据的实例
     */
    public static <E> LinkedList<E> newLinkedList(Collection<? extends E> collection) {
        return new LinkedList<>(collection);
    }

    /**
     * 创建一个可变的 {@code LinkedList}
     * 复制数组中的内容到新创建的{@code LinkedList}中
     *
     * @return 新的{@code List}且带有传入数组的数据的实例
     */
    public static <E> LinkedList<E> newLinkedList(E[] es){
        return new LinkedList<>(java.util.Arrays.asList(es));
    }

    /**
     * 创建一个可变的 {@code Vector}
     * @return 空的 {@code Vector} 实例.
     */
    public static <E> Vector<E> newVector() {
        return new Vector<>();
    }

    /**
     * 创建一个可变的 {@code Vector}
     * 复制一个{@link Vector}中的数据到新的{@code Vector}中
     *
     * @return 新的{@code Vector}且带有传入{@code Vector}数据的实例
     */
    public static <E> Vector<E> newVector(Collection<? extends E> collection) {
        return new Vector<>(collection);
    }

    /**
     * 创建一个可变的 {@code Vector}
     * 复制一个数组中的数据到新的{@code Vector}中
     *
     * @return 新的{@code Vector}且带有传入的数组数据的实例
     */
    public static <E> Vector<E> newVector(E[] es){
        return new Vector<>(Arrays.asList(es));
    }

    public static <T> List<T> asList(Object objects) {
        if(!org.jiakesiws.minipika.framework.tools.Arrays.isArray(objects)) return null;
        return (List<T>) Arrays.asList(objects);
    }

    public static String toString(List<?> list) {
        return JSON.toJSONString(list);
    }
}

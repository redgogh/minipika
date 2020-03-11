package org.raniaia.poseidon.framework.tools;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class Lists {

    public static <E> ArrayList<E> newArrayList(){
        return new ArrayList<>();
    }

    public static <E> LinkedList<E> newLinkedList(){
        return new LinkedList<>();
    }

}

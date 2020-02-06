package org.laniakeamly.poseidon.framework.sql.xml.parser;

import org.jdom2.Content;
import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.sql.xml.node.XMLNode;

/**
 * 解析XML
 * 为了整理所以写了这么一个接口
 * Copyright: Create by TianSheng on 2019/12/17 0:31
 */
interface MapperLabelParserService {

    /**
     * 获取文本文件
     * @param content {@link Content}
     * @return JavaCode
     */
    XMLNode text(Content content);

    /**
     * 解析if标签
     * @param element  当前if标签所属的Element对象
     * @return JavaCode
     */
    XMLNode ifOrEels(Element element);

    /**
     * 解析choose标签并转换成JavaCode
     * @param element       choose标签的Element对象
     * @return JavaCode
     */
    XMLNode choose(Element element);

    /**
     * 解析foreach标签
     * @param element
     * @return
     */
    XMLNode foreach(Element element);

}

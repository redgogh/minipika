package org.maitreya.poseidon.framework.sql.parser;

import org.jdom2.Content;
import org.jdom2.Element;

/**
 * Create by 2BKeyboard on 2019/12/17 0:31
 */
interface XMLParserService {

    /**
     * 获取文本文件
     * @param content {@link Content}
     * @return JavaCode
     */
    String text(Content content);

    /**
     * 解析if标签
     * @param element  当前if标签所属的Element对象
     * @return JavaCode
     */
    String _if(Element element);

    /**
     * 解析choose标签并转换成JavaCode
     * @param element       choose标签的Element对象
     * @return JavaCode
     */
    String choose(Element element);

}

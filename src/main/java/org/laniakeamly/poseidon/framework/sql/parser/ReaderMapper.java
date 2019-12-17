package org.laniakeamly.poseidon.framework.sql.parser;

import org.jdom2.Content;
import org.jdom2.Element;
import org.laniakeamly.poseidon.framework.exception.ExpressionException;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.List;

/**
 * Create by 2BKeyboard on 2019/12/17 10:58
 */
public class ReaderMapper {

    @SuppressWarnings("SpellCheckingInspection")
    public void reader(List<Element> mappers, MapperParser xmlparser) {

        for (Element mapper : mappers) {

            String mappername = mapper.getAttributeValue("name");
            if (StringUtils.isEmpty(mappername)) {
                throw new ExpressionException("tag: mapper attribute name cannot null from builder '" + xmlparser.getCurrentBuilder() + "'");
            }
            xmlparser.setCurrentMapper(mappername);
            //
            // 解析mapper
            //
            parserMapperContent(mapper.getContent(),mappername);
        }

    }

    /**
     * 解析mapper标签下的内容
     * @param contents
     */
    @SuppressWarnings("SpellCheckingInspection")
    public void parserMapperContent(List<Content> contents,String mappername){

        for (Content content : contents) {

            if(content.getCType() == Content.CType.Text){
                String text = StringUtils.trim(content.getValue());
            }

            if(content.getCType() == Content.CType.Element){

            }

        }
        
    }

}

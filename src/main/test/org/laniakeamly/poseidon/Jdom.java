package org.laniakeamly.poseidon;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/17 17:27
 */
public class Jdom {

    @Test
    public void parserNotXMLFile() throws JDOMException, IOException {

        File file = new File("F:\\1ABCDE_FUCKERF\\poseidon\\src\\main\\java\\org\\laniakeamly\\poseidon\\model\\builder\\user_mapper.iakea");

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(file);
        Element rootElement = document.getRootElement();

        System.out.println();

    }


}

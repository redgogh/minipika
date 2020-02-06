package java.poseidon;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright: Create by TianSheng on 2019/12/17 17:27
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

    @Test
    public void findUserByName1111() {
        Map map = new HashMap();
        map.put("age", 100810);
        StringBuilder sql = new StringBuilder();
        sql.append("select * from usaer where 1 = 1");
        if ((java.lang.Integer) map.get("age") < 100086) {
            System.out.println("age小于10086");
        } else {
            System.out.println("age大于10086");
        }
    }

}

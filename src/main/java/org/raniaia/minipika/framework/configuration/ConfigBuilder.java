package org.raniaia.minipika.framework.configuration;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 * Creates on 2020/6/1.
 */

import org.raniaia.minipika.framework.exception.XMLParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author tiansheng
 */
public class ConfigBuilder {

  public ConfigBuilder(String xfile) {
    try {
      getDocument(xfile);
    } catch (Exception e) {
      throw new XMLParseException("Error xml file reading. Cause: " + e.getMessage());
    }
  }

  public void loadProperties() {

  }

  private Document getDocument(String file)
          throws ParserConfigurationException, IOException, SAXException {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse(file);
    return document;
  }

}

package org.jiakesimk.minipika.framework.utils;

/* ************************************************************************
 *
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/7/6.
 */

import org.jdom2.JDOMFactory;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.SAXHandler;
import org.jdom2.input.sax.SAXHandlerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * JDOMHelper来源于网络
 */
public class JDOMHelper {

  private static final SAXHandlerFactory FACTORY = new SAXHandlerFactory() {
    @Override
    public SAXHandler createSAXHandler(JDOMFactory factory) {
      return new SAXHandler() {
        @Override
        public void startElement(
                String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
          super.startElement("", localName, qName, atts);
        }

        @Override
        public void startPrefixMapping(String prefix, String uri) throws SAXException {
        }
      };
    }
  };


  /**
   * Get a {@code SAXBuilder} that ignores namespaces.
   * Any namespaces present in the xml input to this builder will be omitted from the resulting {@code Document}.
   */
  public static SAXBuilder getSAXBuilder() {
    // Note: SAXBuilder is NOT thread-safe, so we instantiate a new one for every call.
    SAXBuilder saxBuilder = new SAXBuilder();
    saxBuilder.setSAXHandlerFactory(FACTORY);
    return saxBuilder;
  }

}

package org.jiakesimk.minipika.framework.configuration;

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

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jiakesimk.minipika.framework.configuration.node.MinipikaXMLConfig;
import org.jiakesimk.minipika.framework.factory.Factorys;
import org.jiakesimk.minipika.framework.logging.Log;
import org.jiakesimk.minipika.framework.logging.LogFactory;
import org.jiakesimk.minipika.framework.util.Charsets;
import org.jiakesimk.minipika.framework.util.Threads;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author tiansheng
 */
public class XMLConfigBuilder {

  final Log log = LogFactory.getLog(XMLConfigBuilder.class);

  private Element root;

  public XMLConfigBuilder() {
  }

  public XMLConfigBuilder(String file) {
    load(file);
  }

  /**
   * 根据输入流去加载配置
   */
  public void load(InputStream in) {
    parseRoot(in);
  }

  /**
   * 根据输出流去加载配置
   */
  public void load(String file) {
    parseRoot(file);
  }

  private void parseRoot(Object object) {
    try {
      Document document = null;
      SAXBuilder builder = Factorys.forClass(SAXBuilder.class);
      if (object instanceof String) {
        document = builder.build((String) object);
      } else if (object instanceof InputStream) {
        document = builder.build(new InputStreamReader((InputStream) object, Charsets.UTF_8));
      }
      this.root = document.getRootElement();
    } catch (Exception e) {
      e.printStackTrace();
      log.error("Error xml file reading. Cause: " + e.getMessage());
    }
  }

  /**
   * 初始化配置类
   */
  public synchronized void initialize() {
    MinipikaXMLConfig minipikaXMLConfig = Factorys.forClass(MinipikaXMLConfig.class);
    minipikaXMLConfig.parse(root);
  }

  private String perfectPath(String path) {
    return Objects.requireNonNull(Threads.getCallerLoader().getResource(path)).toString();
  }

}

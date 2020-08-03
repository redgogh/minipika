package org.jiakesimk.minipika.components.configuration;

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
 * Creates on 2020/6/1.
 */

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jiakesimk.minipika.components.configuration.wrapper.ElementWrapper;
import org.jiakesimk.minipika.components.logging.Log;
import org.jiakesimk.minipika.components.logging.LogFactory;
import org.jiakesimk.minipika.framework.thread.Threads;
import org.jiakesimk.minipika.framework.utils.JDOMHelper;
import org.xml.sax.InputSource;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class XMLConfigBuilder
{

  private ElementWrapper root;

  final Log log = LogFactory.getLog(XMLConfigBuilder.class);

  private XMLConfig config;

  public XMLConfigBuilder()
  {
    this(null);
  }

  public XMLConfigBuilder(String file)
  {
    if (file != null)
    {
      load(file);
    }
  }

  /**
   * 根据输入流去加载配置
   */
  public void load(InputStream in)
  {
    parseRoot(in);
  }

  /**
   * 根据输入流去加载配置
   */
  public void load(InputSource in)
  {
    parseRoot(in);
  }

  /**
   * 根据输出流去加载配置
   */
  public void load(String file)
  {
    parseRoot(file);
  }

  private void parseRoot(Object object)
  {
    try
    {
      Document document = null;
      SAXBuilder builder = JDOMHelper.getSAXBuilder();
      if (object instanceof String)
      {
        document = builder.build((String) object);
      } else if (object instanceof InputStream)
      {
        document = builder.build(new InputStreamReader((InputStream) object));
      } else if (object instanceof InputSource)
      {
        document = builder.build((InputSource) object);
      }
      this.root = new ElementWrapper(document.getRootElement());
      initialize();
    } catch (Exception e)
    {
      e.printStackTrace();
      log.error("Error xml file reading. Cause: " + e.getMessage());
    }
  }

  /**
   * 初始化配置类
   */
  public synchronized void initialize()
  {
    XMLConfig XMLConfig = new XMLConfig();
    XMLConfig.parse(root);
    this.config = XMLConfig;
  }

  public XMLConfig getConfig()
  {
    return this.config;
  }

  private String perfectPath(String path)
  {
    return Objects.requireNonNull(Threads.getCallerLoader().getResource(path)).toString();
  }

}

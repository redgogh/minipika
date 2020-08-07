package org.jiakesimk.minipika.framework.exception;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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

import org.jiakesimk.minipika.components.configuration.wrapper.ElementWrapper;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public class XMLParseException extends BasicException {

  public XMLParseException() {
  }

  public XMLParseException(String message) {
    super(message);
  }

  public XMLParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public XMLParseException(Throwable cause) {
    super(cause);
  }

  public XMLParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * 构建XML异常追踪
   *
   * @param element 目标元素
   */
  public static String buildTrack(ElementWrapper element) {
    String def = buildTrack(element, "");
    def = def.concat(" -> ").concat(element.getName());
    return def;
  }

  private static String buildTrack(ElementWrapper element, String def) {
    ElementWrapper parent = null;
    try {
      parent = element.getParent(); // 获取当前标签的父标签
      if (parent == null) {
        return def;
      }
    } catch (Exception e) {
      if (e instanceof ClassCastException) {
        return def;
      }
      e.printStackTrace();
    }
    String name = parent.getName();
    def = name.concat(" -> ").concat(def);
    def = buildTrack(parent, def);
    return def;
  }

}

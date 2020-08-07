package org.jiakesimk.minipika.framework.utils;

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
 * Creates on 2020/5/15.
 */

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.Charset.forName;

/**
 * 字符集编码,
 *
 * 能够使用{@link Charsets}类来设置编码就尽量使用{@link Charsets}来
 * 设置编码格式, 因为这样减少字符串的查找，能够提升性能
 *
 * @author lts
 * @email jiakesiws@gmail.com
 */
public class Charsets {


  public static final Charset ISO_8859_1            = StandardCharsets.ISO_8859_1;
  public static final Charset US_ASCII              = StandardCharsets.US_ASCII;
  public static final Charset UTF_16                = StandardCharsets.UTF_16;
  public static final Charset UTF_16BE              = StandardCharsets.UTF_16BE;
  public static final Charset UTF_16LE              = StandardCharsets.UTF_16LE;
  public static final Charset UTF_8                 = StandardCharsets.UTF_8;

  public static final Charset GBK                   = forName("GBK");

  public static final Charset DEFAULT_CHARSET       = UTF_8;

  public static Charset toCharset(String charset) {
    return StringUtils.isEmpty(charset) ? UTF_8 : forName(charset);
  }

}
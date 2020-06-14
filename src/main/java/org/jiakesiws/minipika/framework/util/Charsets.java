package org.jiakesiws.minipika.framework.util;

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
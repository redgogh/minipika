package org.jiakesimk.minipika.framework.util;

/*
 * Creates on 2020/5/11.
 */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author lts
 */
public class UrlTools {

  public static String DEFAULT_FORMAT = Charsets.UTF_8.name();

  public static String encode(String input) {
    try {
      return URLEncoder.encode(input, DEFAULT_FORMAT);
    } catch (UnsupportedEncodingException e) {
      return input;
    }
  }

  public static String decode(String input) {
    try {
      return URLDecoder.decode(input, DEFAULT_FORMAT);
    } catch (UnsupportedEncodingException e) {
      return input;
    }
  }

}
package org.raniaia.minipika.framework.util;

/*
 * Creates on 2019/11/13.
 */

/**
 * 验证码生成
 *
 * @author lts
 */
public class Captcha {

  /**
   * 生成6位的数字验证码
   */
  public static int getCode() {
    return (int) ((Math.random() * 9 + 1) * 100000);
  }

}

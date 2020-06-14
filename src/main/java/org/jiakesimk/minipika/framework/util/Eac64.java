package org.jiakesimk.minipika.framework.util;

/*
 * Creates on 2020/5/15.
 */

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * EAC全称是：通用加密算法（Common encryption algorithm）
 *
 * @author lts
 */
public class Eac64 {

  public static void main(String[] args) {
    // 15121116101312
    long x = 15121116101312L;
    long r = x / 32;
    System.out.println(x);
    System.out.println(r);
    System.out.println(r * 32);
  }

  /**
   * MD5加密
   * MD5常用于于密码加密，生成特征值。MD5特点是不可逆
   */
  public static final Md5 MD5 = new Md5();

  /**
   * Base64加密 & 解密
   * Base64加密算法常用于生成图片的base64编码、证书、文件等地方
   */
  public static final Base64Util BASE64 = new Base64Util();

  /**
   * base64工具类
   **/
  public static class Base64Util {

    /*
     * base64加密
     */
    public String encode(String input) {
      byte[] encodeBase64 = Base64.encodeBase64(input.getBytes(Charsets.UTF_8));
      return new String(encodeBase64, Charsets.UTF_8);
    }

    /*
     * base64解密
     */
    public String decode(String base64) {
      byte[] decodes = Base64.decodeBase64(base64.getBytes(Charsets.UTF_8));
      return new String(decodes, Charsets.UTF_8);
    }
  }

  /**
   * MD5工具类
   **/
  public static class Md5 {

    @SneakyThrows
    public String digest(byte[] source) {
      String output;
      char[] hexDigits = { // 用来将字节转换成 16 进制表示的字符
              '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(source);
      byte[] tmp = md.digest();
      char[] str = new char[16 * 2];
      int k = 0;
      for (int i = 0; i < 16; i++) {
        byte byte0 = tmp[i];
        str[k++] = hexDigits[byte0 >>> 4 & 0xf];
        str[k++] = hexDigits[byte0 & 0xf];
      }
      output = new String(str);
      return output;
    }

    public String encode(String input) {
      return digest(input.getBytes());
    }

    public String encode32(String input) {
      return digest32(input);
    }

    /*
     * md5采用32位大写
     */
    @SneakyThrows
    public String digest32(String text) {
      MessageDigest digest = MessageDigest.getInstance("md5");
      byte[] result = digest.digest(text.getBytes());
      StringBuilder sb = new StringBuilder();
      for (byte b : result) {
        int number = b & 0xff;
        String hex = Integer.toHexString(number);
        if (hex.length() == 1) {
          sb.append("0").append(hex);
        } else {
          sb.append(hex);
        }
      }
      return sb.toString().toUpperCase();
    }

  }

}
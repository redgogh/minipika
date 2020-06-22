package org.jiakesimk.minipika.framework.util;

/*
 * Creates on 2020/3/11.
 */

import org.jiakesimk.minipika.framework.factory.Factorys;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author lts
 */
public
class StringUtils {

  // unicode下的空格 \u0000
  public static final String UNICODE_U0000 = "\u0000";

  // unicode下的空格 \u3000
  public static final String UNICODE_U3000 = "\u3000";

  /**
   * {@link #cutstring}方法截取的字符串包含index
   */
  public static final int INCLUDE_INDEXSTR = -1;

  /**
   * {@link #cutstring}方法截取的字符串不包含index
   */
  public static final int NONINCLUDE_INDEXSTR = -2;

  /**
   * 字符串是否为空
   *
   * @param input 目标字符串
   * @return true为空，false反之。
   */
  public static boolean isEmpty(String input) {
    return input == null
            || input.length() == 0
            || UNICODE_U0000.equals(input)
            || UNICODE_U3000.equals(input);
  }

  /**
   * 判断字符串是否不为空
   *
   * @return true表示当前不为空，false反之
   */
  public static boolean isNotEmpty(String input) {
    return !isEmpty(input);
  }

  /**
   * 判断是不是null字符，或者是空
   */
  public static boolean isNull(String input) {
    return input == null || "null".equals(input);
  }

  public static String requireNonNull(String input, String message) {
    return requireNonNull(input, message, NullPointerException.class);
  }

  public static String requireNonNull(String input, String message, Class<? extends RuntimeException> e) {
    if (isEmpty(input)) {
      RuntimeException e1 = Factorys.forClass(e, new Class[]{String.class}, message);
      throw e1;
    }
    return input;
  }

  /**
   * 截取0到index之间的字符串
   *
   * @param input 源字符串
   * @param index 截取到哪个位置
   */
  public static String cutstring(String input, String index) {
    if (!input.contains(index)) {
      return input;
    }
    int ofIndex = input.indexOf(index);
    return input.substring(0, ofIndex);
  }

  /**
   * 截取from到index之间的字符，如果from是负值的话那就代表是截取
   * from到input.length()之间的字符串
   *
   * @param input 源字符串
   * @param index 截取的字符串
   * @param from  从哪个位置开始截取，如果from是负数那么就代表是
   *              截取index到input.length()之间的值
   */
  public static String cutstring(String input, String index, int from) {
    if (!input.contains(index)) {
      return input;
    }
    int ofIndex = input.indexOf(index);
    if (from >= 0) {
      return input.substring(from, ofIndex);
    }
    if (from == INCLUDE_INDEXSTR) {
      return input.substring((ofIndex));
    }
    if (from == NONINCLUDE_INDEXSTR) {
      return input.substring((ofIndex + 1));
    }
    return null;
  }

  /**
   * 获取最后一个字符
   *
   * @param input 目标字符串
   * @return 返回该字符串的最后一个字符
   */
  public static String getLast(String input) {
    return input.substring(input.length() - 1);
  }

  /**
   * 删除最后一个字符
   *
   * @param input 目标字符串
   * @return 返回处理后的字符串
   */
  public static String delLast(String input) {
    return input.substring(0, input.length() - 1);
  }

  /**
   * 获取首字符
   *
   * @param input
   * @return
   */
  public static String getFirst(String input) {
    return input.substring(0, 1);
  }

  /**
   * 删除第一个字符
   *
   * @param input 目标字符串
   * @return 返回处理后的字符串
   */
  public static String delFirst(String input) {
    return input.substring(1, input.length());
  }

  /**
   * 删除首尾字符
   */
  public static String delHeadAndTail(String input) {
    return delFirst(delLast(input));
  }

  /**
   * 判断字符串是不是数字
   */
  public static boolean isNumber(String input) {
    Pattern pattern = Pattern.compile("[0-9]*");
    return pattern.matcher(input).matches();
  }

  public static String asString(Object input) {
    return asString(input, null);
  }

  public static String asString(Object input, String def) {
    try {
      if (input != null) {
        if (input instanceof String) {
          return (String) input;
        } else {
          return input.toString();
        }
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static int asInt(Object input) {
    return asInt(input, 0);
  }

  public static int asInt(Object input, int def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Integer.parseInt(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static Integer asInt0(Object input) {
    return asInt0(input, null);
  }

  public static Integer asInt0(Object input, Integer def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Integer.valueOf(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static long asLong(Object input) {
    return asLong(input, 0L);
  }

  public static long asLong(Object input, long def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Long.parseLong(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static Long asLong0(Object input) {
    return asLong0(input, null);
  }

  public static Long asLong0(Object input, Long def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Long.valueOf(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static float asFloat(Object input) {
    return asFloat(input, 0);
  }

  public static float asFloat(Object input, float def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Float.parseFloat(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static Float asFloat0(Object input) {
    return asFloat0(input, null);
  }

  public static Float asFloat0(Object input, Float def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Float.valueOf(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static double asDouble(Object input) {
    return asDouble(input, 0);
  }

  public static double asDouble(Object input, double def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Double.parseDouble(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static Double asDouble0(Object input) {
    return asDouble0(input, null);
  }

  public static Double asDouble0(Object input, Double def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Double.valueOf(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static boolean asBoolean(Object input) {
    return asBoolean(input, false);
  }

  public static boolean asBoolean(Object input, boolean def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Boolean.parseBoolean(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static Boolean asBoolean0(Object input) {
    return asBoolean0(input, null);
  }

  public static Boolean asBoolean0(Object input, Boolean def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return Boolean.valueOf(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  public static BigDecimal asBigDecimal(Object input) {
    return asBigDecimal(input, null);
  }

  public static BigDecimal asBigDecimal(Object input, BigDecimal def) {
    try {
      if (input != null) {
        String strValue = asString(input);
        return new BigDecimal(strValue);
      }
    } catch (Exception ignored) {
    }
    return def;
  }

  /**
   * String格式化,大约比String.format()快17倍
   * 格式化的字符为两个花括号"{}"
   */
  public static String format(String input, Object... args) {
    if (isEmpty(input)) {
      return input;
    }
    int argsLen = 0;
    int offset = 0;
    int subscript = 0;
    char[] chars = input.toCharArray();
    StringBuilder builder = new StringBuilder();
    char previous = '#';
    for (int i = 0; i < chars.length; i++) {
      char current = chars[i];
      if (previous == '{' && current == '}') {
        if (argsLen >= args.length) {
          return builder.toString().concat(new String(chars).substring((i + 1)));
        }
        char[] temp = new char[(i - offset) - 1];
        System.arraycopy(chars, offset, temp, 0, (offset = i - 1));
        builder.append(temp).append(args[subscript]);
        temp = new char[chars.length - offset - 2];
        System.arraycopy(chars, offset + 2, temp, 0, temp.length);
        // reset
        chars = temp;
        subscript++;
        i = 0;
        offset = 0;
        argsLen++;
      } else {
        previous = current;
      }
    }
    return builder.append(chars).toString();
  }

  /**
   * 将字符串合并成一行
   */
  public static String mergeOneLine(String text) {
    StringBuilder content = new StringBuilder();
    StringTokenizer tokenizer = new StringTokenizer(text);
    while (tokenizer.hasMoreTokens()) {
      String str = tokenizer.nextToken();
      content.append(str);
      if (tokenizer.hasMoreTokens()) {
        content.append(" ");
      }
    }
    return content.toString();
  }

  /**
   * 替换掉所有为空格的字符
   */
  public static String replaceNull(String input, String... patterns) {
    for (String pattern : patterns) {
      input = input.replaceAll(pattern, "");
    }
    return input.trim().replaceAll(" ", "").trim();
  }

  /**
   * 对某个字母转换成大写
   *
   * @param index 字符串的下标
   */
  public static String toUpperCase(String input, int index) {
    StringBuilder builder = new StringBuilder(input);
    String value = new String(new char[]{input.charAt(index - 1)}).toUpperCase();
    builder.replace(0, 1, value);
    return builder.toString();
  }

  public static String toUpperCase(String input) {
    if (input == null) return input;
    return input.toUpperCase();
  }

  /**
   * 对某个字母转换成小写
   *
   * @param index 字符串的下标
   */
  public static String toLowerCase(String input, int index) {
    StringBuilder builder = new StringBuilder(input);
    String value = new String(new char[]{input.charAt(index - 1)}).toLowerCase();
    builder.replace(0, 1, value);
    return builder.toString();
  }

  public static String toLowerCase(String input) {
    return input.toLowerCase();
  }

  /**
   * 驼峰转下划线
   */
  public static String humpToUnderline(String string) {
    StringBuilder builder = new StringBuilder(string);
    int temp = 0; // 定位
    for (int i = 0, len = string.length(); i < len; i++) {
      if (Character.isUpperCase(string.charAt(i))) {
        builder.insert(i + temp, "_");
        temp++;
      }
    }
    return builder.toString().toLowerCase();
  }

  /**
   * 下划线转驼峰
   */
  public static String underlineToHump(String string) {
    return characterToHump(string, "_");
  }

  /**
   * 根据某个字符分割然后转驼峰命名
   */
  public static String characterToHump(String string, String ch) {
    StringBuilder builder = new StringBuilder();
    String[] strs = string.split(ch);
    builder.append(strs[0]);
    for (int i = 1; i < strs.length; i++) {
      StringBuilder v = new StringBuilder(strs[i]);
      v.replace(0, 1, String.valueOf(v.charAt(0)).toUpperCase());
      builder.append(v);
    }
    return builder.toString();
  }

  /**
   * 将String数组合并成单个String字符串
   */
  public static String newString(String[] inputs) {
    StringBuilder builder = new StringBuilder();
    for (String input : inputs) {
      builder.append(input);
    }
    return builder.toString();
  }

  /**
   * 获取异常的堆栈打印
   */
  public static String getStackTrace(Throwable e) {
    StringWriter strWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(strWriter);
    try {
      e.printStackTrace(printWriter);
      strWriter.close();
      printWriter.close();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
    return strWriter.toString();
  }

}


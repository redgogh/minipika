package org.jiakesimk.minipika.framework.util;

/*
 * Creates on 2019/12/7.
 */

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 计算{@code String}中的表达式
 *
 * @author lts
 */
public final class Calculator {

  private String exps;

  private Map<Integer, Status> posMap;

  private Long SUM = 0L;

  /**
   * 状态
   */
  enum Status {ADD, SUBTRACT, MULTIPLY, DIVIDE}

  public Long express(String exps) {
    this.exps = clearSpace(exps);
    getPosition(); // 获取运算符的位置
    calc();
    return SUM;
  }


  // 测试
  public static void main(String[] args) {
    Calculator calculator = new Calculator();
    Long sum = calculator.express("2 * ((( 2 + 3 ) * (4 * 2) + ( 3 * 3)) * ( 2 + 3 ) * (4 * 2) + ( 3 * 3))");
    System.err.println(sum);
    System.out.println(2 * (((2 + 3) * (4 * 2) + (3 * 3)) * (2 + 3) * (4 * 2) + (3 * 3)));
  }

  /**
   * 获取各个运算符号的位置
   */
  private void getPosition() {
    posMap = new LinkedHashMap<>();
    char[] chars = exps.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      char value = chars[i];
      switch (value) {
        case '+': {
          posMap.put(i + 1, Status.ADD);
          break;
        }
        case '-': {
          posMap.put(i + 1, Status.SUBTRACT);
          break;
        }
        case '*': {
          posMap.put(i + 1, Status.MULTIPLY);
          break;
        }
        case '/': {
          posMap.put(i + 1, Status.DIVIDE);
          break;
        }
      }
    }
  }

  /**
   * 计算
   *
   * @return
   */
  private void calc() {
    if (!exps.contains("+")
            && !exps.contains("-")
            && !exps.contains("*")
            && !exps.contains("/")) {
      SUM = Long.valueOf(exps);
    }
    // 首先处理括号中的运算
    brackets(this.exps);
    // 第一次做乘除运算
    for (Map.Entry<Integer, Status> pos : posMap.entrySet()) {
      if (posMap.size() == 0) break;
      Status status = pos.getValue();
      if (status == Status.MULTIPLY || status == Status.DIVIDE) {
        multiplyDivide(pos.getKey(), status);
        calc();
      }
    }
    // 第二次做加减运算
    for (Map.Entry<Integer, Status> pos : posMap.entrySet()) {
      if (posMap.size() == 0) break;
      Status status = pos.getValue();
      if (status == Status.ADD || status == Status.SUBTRACT) {
        addSubtract(pos.getKey(), status);
        calc();
      }
    }
  }

  /**
   * 乘法运算
   *
   * @param position
   * @param status
   */
  private void multiplyDivide(Integer position, Status status) {
    if (status == Status.MULTIPLY) {
      include(position, Status.MULTIPLY);
    }
    if (status == Status.DIVIDE) {
      include(position, Status.DIVIDE);
    }
  }

  /**
   * 加减运算
   *
   * @param position
   * @param status
   */
  private void addSubtract(Integer position, Status status) {
    if (status == Status.ADD) {
      include(position, Status.ADD);
    }
    if (status == Status.SUBTRACT) {
      include(position, Status.SUBTRACT);
    }
  }

  /**
   * 处理括号中的运算
   */
  private void brackets(String exps) {
    Integer[] offset = new Integer[2];
    char[] chars = exps.toCharArray();
    for (int i = 0; i < chars.length; i++) {
      char value = chars[i];
      if (value == '(') offset[0] = i + 1;
      if (value == ')' && offset[1] == null) {
        offset[1] = i;
        break;
      }
    }
    if (offset[0] != null && offset[1] != null) {
      String exp1 = exps.substring(offset[0], offset[1]);
      Calculator calculator = new Calculator();
      Long value = calculator.express(exp1);
      this.exps = new StringBuilder(this.exps).replace(offset[0] - 1, offset[1] + 1, value.toString()).toString();
      //
      // 递归处理，直到表达式中没有括号为止
      //
      brackets(this.exps);
    }
  }

  /**
   * 将从position开始的位置计算并返回结果
   *
   * @param position
   * @return
   */
  private long include(int position, Status status) {
    int[] offset = new int[2];
    Long first = null;
    Long second = null;
    String temp = null;
    char[] chars = exps.toCharArray();
    //
    // 推导出从position开始的上一个数字
    //
    temp = exps.substring(0, position - 1);
    if (temp.contains("+")
            || temp.contains("-")
            || temp.contains("*")
            || temp.contains("/")) {
      int iPos = position - 1;
      for (int i = iPos - 1; i > 0; i--) {
        char value = chars[i];
        switch (value) {
          case '+':
          case '-':
          case '*':
          case '/': {
            i = i + 1;
            temp = exps.substring(i, iPos);
            offset[0] = i;
            i = 0;
            break;
          }
        }
      }
      first = Long.valueOf(temp);
    } else {
      first = Long.valueOf(temp);
      offset[0] = 0;
    }
    //
    // 推导出从position开始的下一个数字
    //
    temp = exps.substring(position);
    if (temp.contains("+")
            || temp.contains("-")
            || temp.contains("*")
            || temp.contains("/")) {

      int iPos = position + 1;
      for (int i = iPos; i < chars.length; i++) {
        char value = chars[i];
        switch (value) {
          case '+':
          case '-':
          case '*':
          case '/': {
            iPos = iPos - 1;
            temp = exps.substring(iPos, i);
            offset[1] = i;
            i = chars.length + 1;
            break;
          }
        }
      }
      second = Long.valueOf(temp);
    } else {
      second = Long.valueOf(temp);
      offset[1] = chars.length;
    }
    // 返回结果
    long sum = 0L;
    if (status == Status.ADD)
      sum = first + second;
    if (status == Status.SUBTRACT)
      sum = first - second;
    if (status == Status.MULTIPLY)
      sum = first * second;
    if (status == Status.DIVIDE)
      sum = first / second;
    reset(new StringBuilder(exps).replace(offset[0], offset[1], String.valueOf(sum)).toString());
    return sum;
  }

  /**
   * 清除空格
   *
   * @param exps
   * @return
   */
  private String clearSpace(String exps) {
    StringBuilder builder = new StringBuilder();
    char[] chars = exps.toCharArray();
    for (char value : chars) {
      switch (value) {
        case ' ':
          break;
        default:
          builder.append(value);
      }
    }
    return builder.toString();
  }

  /**
   * 重新设置
   *
   * @param exps
   */
  private void reset(String exps) {
    this.exps = clearSpace(exps);
    getPosition(); // 获取运算符的位置
  }

}

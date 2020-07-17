package jni;

/*
 * Creates on 2019/11/13.
 */

import org.jiakesimk.minipika.framework.util.Objects;
import org.junit.Test;

/**
 * @author lts
 */
public class ObjectUtilsTest {

  public static void main(String[] args) {
    System.loadLibrary("o");
    Object o = new Object();
    System.out.println(Objects.sizeof(o));
  }

}

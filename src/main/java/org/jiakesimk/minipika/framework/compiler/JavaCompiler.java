package org.jiakesimk.minipika.framework.compiler;

/*
 * Creates on 2020/3/24.
 */

import org.jiakesimk.minipika.framework.utils.Charsets;

import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;

/**
 * Java compiler
 *
 * @author lts
 * @email ltsloveyellow@aliyun.com
 */
public class JavaCompiler {

  /**
   * 将String类型的字符串编译成Java代码并返回Class对象。
   *
   * @param fullClassname 全类名。
   * @param code          需要编译的字符串源码.
   * @return {@code Class}对象实例.
   */
  public static Class<?> compile(String fullClassname, String code) {
    javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null,
            Charsets.GBK);
    JavaFileSource srcObject = new JavaFileSource(fullClassname, code);
    Iterable<? extends JavaFileObject> fileObjects = Collections.singletonList(srcObject);
    String flag = "-d";
    String outDir = "";
    try {
      File classPath = new File(Thread.currentThread().getContextClassLoader().getResource("").toURI());
      outDir = classPath.getAbsolutePath() + File.separator;
    } catch (URISyntaxException e1) {
      e1.printStackTrace();
    }
    Iterable<String> options = Arrays.asList(flag, outDir);
    javax.tools.JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
            null, options, null, fileObjects);
    boolean result = task.call();
    if (result) {
      try {
        return Class.forName(fullClassname);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

}

package test;

/*
 * Creates on 2019/11/13.
 */

import com.minipika.mapper.UserMapper;
import javassist.*;
import org.junit.Test;

/**
 * @author lts
 */
public class JavassistTest {

  @Test
  public void test() throws NotFoundException {
    ClassPool p = new ClassPool();
    ClassClassPath path = new ClassClassPath(UserMapper.class);
    p.insertClassPath(path);
    CtClass c = p.get("com.minipika.mapper.UserMapper");
    CtMethod methods[] = c.getDeclaredMethods();
    System.out.println();
  }

}

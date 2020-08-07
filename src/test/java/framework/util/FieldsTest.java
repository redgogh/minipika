package framework.util;

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
 * Creates on 2020/6/1.
 */

import org.jiakesiws.minipika.framework.utils.Arrays;
import org.junit.Test;
import testObject.Entity;
import org.jiakesiws.minipika.framework.annotations.Component;
import org.jiakesiws.minipika.framework.utils.Fields;

import java.lang.reflect.Field;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
public class FieldsTest {

  @Test
  public void getFieldsIncludeSuper() {

    Field[] fields = Fields.getDeclaredFieldsIncludeSuper(Entity.class, true);
    System.out.println(Arrays.toString(fields));
    System.out.println("======================================================");
    fields = Fields.getDeclaredFieldsIncludeSuper(Entity.class, true, new Class[]{Component.class});
    System.out.println(Arrays.toString(fields));

  }

}

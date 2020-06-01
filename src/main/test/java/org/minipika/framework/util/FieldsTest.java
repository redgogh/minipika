package org.minipika.framework.util;

/*
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 */

/*
 * Creates on 2020/6/1.
 */

import org.junit.Test;
import org.minipika.testObject.MinipikaEntity;
import org.raniaia.minipika.framework.annotations.Minipika;
import org.raniaia.minipika.framework.util.ArrayUtils;
import org.raniaia.minipika.framework.util.Fields;

import java.lang.reflect.Field;

/**
 * @author tiansheng
 */
public class FieldsTest {

  @Test
  public void getFieldsIncludeSuper() {

    Field[] fields = Fields.getDeclaredFieldsIncludeSuper(MinipikaEntity.class, true);
    System.out.println(ArrayUtils.toString(fields));
    System.out.println("======================================================");
    fields = Fields.getDeclaredFieldsIncludeSuper(MinipikaEntity.class, true, new Class[]{Minipika.class});
    System.out.println(ArrayUtils.toString(fields));

  }

}

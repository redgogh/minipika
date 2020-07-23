package groovy;

/* ************************************************************************
 *
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
 *
 * ************************************************************************/

/*
 * Creates on 2020/6/18.
 */

import groovy.lang.Closure;
import org.jiakesimk.minipika.components.mql.MqlCallback;
import org.jiakesimk.minipika.framework.utils.ArrayUtils;
import org.jiakesimk.minipika.framework.utils.Lists;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
public class ForJava {

  @Test
  public void test() {
    String[] s = MatchesDemo.find("username = #username and password = #password", "#(.*?)\\S+", new Closure(null) {
      @Override
      public Object call(Object... args) {
        return args[0] + "xxxx";
      }
    });
    System.out.println(ArrayUtils.toString(s));
  }

  @Test
  public void getParameterNamesTest() throws NoSuchMethodException {
    Method method = MqlMapper.class.getDeclaredMethod("addBatch", List.class);
    for(Parameter parameter : method.getParameters()) {
      System.out.println(parameter.getName());
    }
  }

  @Test
  public void returnTest() {
    MqlCallback m = new MqlCallback(MqlMapper.class);
    MqlMapper mapper = m.bind();
    List<User> users = (List<User>) mapper.findUser(new User("key", "value"));
    System.out.println(Lists.toString(users));
  }

}

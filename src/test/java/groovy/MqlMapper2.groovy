package groovy

import org.jiakesimk.minipika.components.annotation.SQL;

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
 * Creates on 2020/6/30.
 */

/**
 * @author tiansheng
 */
interface MqlMapper2 {

  @SQL("""
    select * from minipika_user where 1=1
    #if INE(user.name) && user.name != null
      and username = #{user.name}
    #end
    and wdnmd = #{wdnmd}
  """)
  def findUser(User user, String wdnmd);

}
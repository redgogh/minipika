package org.raniaia.minipika.framework.tools;

/*
 * Copyright (C) 2020 Tiansheng All rights reserved.
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
 * Creates on 2020/2/15.
 */

import org.raniaia.minipika.framework.provide.entity.Entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tiansheng
 */
public class JdbcUtils {

    /**
     * 生成insert语句
     * @param obj    entity对象
     * @return
     * @throws Exception
     */
    public static String generateInsertSQL(Object obj,List<Object> params) {
        try {
            StringBuffer into = new StringBuffer("insert into ");
            StringBuffer values = new StringBuffer(" values ");
            Class<?> target = obj.getClass();
            if (SecurityManager.existEntity(target)) {
                Entity entity = EntityUtils.getEntityAnnotation(target);
                into.append("`").append(entity.value()).append("`");
            }
            into.append("(");
            values.append("(");
            List<Field> fields = EntityUtils.getEntityField(obj);
            for (Field field : fields) {
                Object v = field.get(obj);
                if (v != null) {
                    into.append("`").append(EntityUtils.humpToUnderline(field.getName())).append("`,");
                    values.append("?,");
                    params.add(v);
                }
            }
            into.deleteCharAt((into.length() - 1));
            into.append(")");
            values.deleteCharAt((values.length() - 1));
            values.append(")");
            into.append(values);
            return into.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateInsertBatchSQL(List<Object> objs,List<Object[]> params0){
        String sql = null;
        for (Object obj : objs) {
            List params1 = new ArrayList();
            if(StringUtils.isEmpty(sql)) {
                sql = generateInsertSQL(obj, params1);
            }else{
                generateInsertSQL(obj, params1);
            }
            params0.add(params1.toArray());
        }
        return sql;
    }

}

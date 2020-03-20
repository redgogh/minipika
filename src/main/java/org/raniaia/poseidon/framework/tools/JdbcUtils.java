package org.raniaia.poseidon.framework.tools;

import org.raniaia.poseidon.framework.provide.model.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright by tiansheng on 2020/2/15 1:17
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class JdbcUtils {

    /**
     * 生成insert语句
     * @param obj    model对象
     * @return
     * @throws Exception
     */
    public static String generateInsertSQL(Object obj,List<Object> params) {
        try {
            StringBuffer into = new StringBuffer("insert into ");
            StringBuffer values = new StringBuffer(" values ");
            Class<?> target = obj.getClass();
            if (SecurityManager.existModel(target)) {
                Model model = ModelUtils.getModelAnnotation(target);
                into.append("`").append(model.value()).append("`");
            }
            into.append("(");
            values.append("(");
            List<Field> fields = ModelUtils.getModelField(obj);
            for (Field field : fields) {
                Object v = field.get(obj);
                if (v != null) {
                    into.append("`").append(ModelUtils.humpToUnderline(field.getName())).append("`,");
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

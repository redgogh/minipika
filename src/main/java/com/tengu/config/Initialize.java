package com.tengu.config;

import com.tengu.annotation.Model;
import com.tengu.db.JdbcFunction;
import com.tengu.db.NativeJdbc;
import com.tengu.exception.ParseException;
import com.tengu.model.IndexAttribute;
import com.tengu.model.IndexModel;
import com.tengu.model.ModelAttribute;
import com.tengu.model.ParseModel;
import com.tengu.tools.TenguUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 初始化
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/19 18:05
 * @since 1.8
 */
public class Initialize extends NativeJdbc{

    // 添加字段
    private static final String ADD_COLUMN_SCRIPT = "ALTER TABLE `%s` ADD %s after `%s`;";

    /**
     * 解析model
     */
    public void parseModel() {
        ParseModel parseModel = new ParseModel();
        parseModel.parse(TenguUtils.getModels());
        Map<String, ModelAttribute> messages = ModelAttribute.getAttribute();
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, ModelAttribute> entry = (Map.Entry<String, ModelAttribute>) iter.next();
            ModelAttribute message = entry.getValue();
            JdbcFunction.getFunction().execute(message.getCreateTableSql());
        }
    }

    /**
     * 检测是否有新增的字段
     *
     * @throws ParseException
     */
    public void columnCheck() throws ParseException {
        List<Class<?>> models = TenguUtils.getModels();
        for (Class<?> target : models) {
            if (target.isAnnotationPresent(Model.class)) {
                Model model = target.getDeclaredAnnotation(Model.class);
                String table = model.value();
                List<String> inDbColumns = JdbcFunction.getFunction().getColumns(table);
                ModelAttribute message = ModelAttribute.getAttribute().get(table);
                Map<String, String> inMessageColumns = message.getColumns();
                Iterator iter = inMessageColumns.entrySet().iterator();
                String previousKey = null;
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey();
                    if (!inDbColumns.contains(key)) {
                        String executeScript = String.format(ADD_COLUMN_SCRIPT, message.getTableName(), entry.getValue(),previousKey);
                        execute(executeScript);
                    }
                    previousKey = key;
                }
            } else {
                throw new ParseException("没有@Model注解");
            }
        }
    }

    public void indexesCheck(){
        for(Map.Entry<String,ModelAttribute> entryMap : ModelAttribute.getAttribute().entrySet()){

            List<IndexModel> indexes = JdbcFunction.getFunction().getIndexes(entryMap.getKey());

            ModelAttribute modelAttribute = entryMap.getValue();
            List<IndexAttribute> modelIndex = modelAttribute.getIndexes();

            System.out.println();

        }
    }

}

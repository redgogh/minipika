package com.tengu.config;

import com.alibaba.fastjson.JSONObject;
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
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/19 18:05
 * @since 1.8
 */
public class Initialize extends NativeJdbc {

    // 添加字段
    private final String ADD_COLUMN_SCRIPT = "ALTER TABLE `%s` ADD %s after `%s`;";

    public void run() throws ParseException {
        loadModel();
        loadColumn();
        loadIndex();
    }

    private final String UNIQUE_NON = "1"; // 代表索引不唯一
    private final String UNIQUE_YES = "0"; // 代表索引是唯一

    /**
     * 解析model
     */
    public void loadModel() {
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
    public void loadColumn() throws ParseException {
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
                        String executeScript = String.format(ADD_COLUMN_SCRIPT, message.getTableName(), entry.getValue(), previousKey);
                        execute(executeScript);
                    }
                    previousKey = key;
                }
            } else {
                throw new ParseException("没有@Model注解");
            }
        }
    }

    public void loadIndex() {
        for (Map.Entry<String, ModelAttribute> entryMap : ModelAttribute.getAttribute().entrySet()) {

            // 数据库中的索引信息
            List<IndexModel> indexes = JdbcFunction.getFunction().getIndexes(entryMap.getKey());

            // 本地的索引信息
            ModelAttribute modelAttribute = entryMap.getValue();
            List<IndexAttribute> attributes = modelAttribute.getIndexes();

            for (IndexAttribute attribute : attributes) {
                for (IndexModel model : indexes) {

                    switch (attribute.getType()) {
                        // 普通索引
                        case NORMAL: {
                            break;
                        }
                        // 全文索引
                        case FULLTEXT: {
                            break;
                        }
                        // 唯一索引
                        case UNIQUE: {
                            break;
                        }
                        // 空间索引
                        case SPATIAL: {
                            break;
                        }
                    }
                }
            }

            System.out.println();

        }
    }

}

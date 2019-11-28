package com.tractor.config;

import com.alibaba.fastjson.JSONObject;
import com.tractor.annotation.Model;
import com.tractor.db.JdbcSupport;
import com.tractor.exception.TractorException;
import com.tractor.model.SecurityManager;
import com.tractor.model.Metadata;
import com.tractor.model.GetterModel;
import com.tractor.tools.TractorUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 初始化
 *
 * @author 2Bkeyboard
 * @version 1.0.0
 * @date 2019/11/19 18:05
 * @since 1.8
 */
public class Initialize extends JdbcSupport {

    // 添加字段
    private final String ADD_COLUMN_SCRIPT = "ALTER TABLE `%s` ADD %s after `%s`;";

    public void run() throws TractorException {
        loadModel();
        loadColumn();
    }

    /**
     * 解析model
     */
    public void loadModel() {
        GetterModel getterModel = new GetterModel();
        getterModel.parse(TractorUtils.getModels());
        Map<String, Metadata> messages = Metadata.getAttribute();
        String sql = "show table status from %s where name = '%s';";
        String updateEngineSql = "ALTER TABLE %s ENGINE = '%s'";
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Metadata> entry = (Map.Entry<String, Metadata>) iter.next();
            Metadata metadata = entry.getValue();
            String tableName = metadata.getTableName();
            execute(metadata.getCreateTableSql());
            // 判断储存引擎是被修改
            sql = String.format(sql, Config.getDbname(), tableName);
            String jsonString = queryForJson(sql);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            if (!String.valueOf(metadata.getEngine()).toUpperCase()
                    .equals(jsonObject.getString("Engine").toUpperCase())) {
                updateEngineSql = String.format(updateEngineSql, tableName, metadata.getEngine());
                update(updateEngineSql);
            }
        }
    }

    /**
     * 检测是否有新增的字段
     *
     * @throws TractorException
     */
    public void loadColumn() throws TractorException {
        List<Class<?>> models = TractorUtils.getModels();
        for (Class<?> target : models) {
            if (SecurityManager.existModel(target)) {
                Model model = TractorUtils.getModelAnnotation(target);
                String table = model.value();
                List<String> inDbColumns = getColumns(table);
                Metadata metadata = Metadata.getAttribute().get(table);
                Map<String, String> inMessageColumns = metadata.getColumns();
                Iterator iter = inMessageColumns.entrySet().iterator();
                String previousKey = null;
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey();
                    if (!inDbColumns.contains(key)) {
                        String executeScript = String.format(ADD_COLUMN_SCRIPT, metadata.getTableName(), entry.getValue(), previousKey);
                        execute(executeScript);
                    }
                    previousKey = key;
                }
            } else {
                throw new TractorException("No @Model");
            }
        }
    }

}

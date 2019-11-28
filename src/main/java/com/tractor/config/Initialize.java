package com.tractor.config;

import com.alibaba.fastjson.JSONObject;
import com.tractor.annotation.Model;
import com.tractor.db.JdbcSupport;
import com.tractor.db.NativeJdbc;
import com.tractor.exception.TractorException;
import com.tractor.model.SecurityManager;
import com.tractor.model.GlobalMsg;
import com.tractor.model.ModelGetter;
import com.tractor.tools.TractorUtils;

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

    public void run() throws TractorException {
        loadModel();
        loadColumn();
    }

    /**
     * 解析model
     */
    public void loadModel() {
        ModelGetter modelGetter = new ModelGetter();
        modelGetter.parse(TractorUtils.getModels());
        Map<String, GlobalMsg> messages = GlobalMsg.getAttribute();
        String sql = "show table status from %s where name = '%s';";
        String updateEngineSql = "ALTER TABLE %s ENGINE = '%s'";
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, GlobalMsg> entry = (Map.Entry<String, GlobalMsg>) iter.next();
            GlobalMsg globalMsg = entry.getValue();
            String tableName = globalMsg.getTableName();
            JdbcSupport.getTemplate().execute(globalMsg.getCreateTableSql());
            // 判断储存引擎是被修改
            sql = String.format(sql, Config.getDbname(), tableName);
            String jsonString = executeQuery(sql).toJSONString();
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            if (!String.valueOf(globalMsg.getEngine()).toUpperCase()
                    .equals(jsonObject.getString("Engine").toUpperCase())) {
                updateEngineSql = String.format(updateEngineSql, tableName, globalMsg.getEngine());
                executeUpdate(updateEngineSql);
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
                List<String> inDbColumns = JdbcSupport.getTemplate().getColumns(table);
                GlobalMsg globalMsg = GlobalMsg.getAttribute().get(table);
                Map<String, String> inMessageColumns = globalMsg.getColumns();
                Iterator iter = inMessageColumns.entrySet().iterator();
                String previousKey = null;
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey();
                    if (!inDbColumns.contains(key)) {
                        String executeScript = String.format(ADD_COLUMN_SCRIPT, globalMsg.getTableName(), entry.getValue(), previousKey);
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

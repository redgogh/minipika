package com.poseidon.framework.config;

import com.alibaba.fastjson.JSONObject;
import com.poseidon.framework.annotation.Assembly;
import com.poseidon.framework.annotation.Model;
import com.poseidon.framework.beans.BeansManager;
import com.poseidon.framework.db.JdbcSupport;
import com.poseidon.framework.exception.PoseidonException;
import com.poseidon.framework.model.SecurityManager;
import com.poseidon.framework.model.Metadata;
import com.poseidon.framework.model.GetterModel;
import com.poseidon.framework.tools.StringUtils;
import com.poseidon.framework.tools.PoseidonUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 初始化
 *
 * @author 2BKeyboard
 * @version 1.0.0
 * @date 2019/11/19 18:05
 * @since 1.8
 */
public class Initialize {

    // 添加字段
    private String ADD_COLUMN_SCRIPT = "ALTER TABLE `{}` ADD {} after `{}`;";

    private JdbcSupport jdbc = BeansManager.newJdbcSupport();

    public void run() throws PoseidonException {
        loadModel();
        loadColumn();
    }

    /**
     * 解析model
     */
    public void loadModel() {
        GetterModel getterModel = new GetterModel();
        getterModel.parse(PoseidonUtils.getModels());
        Map<String, Metadata> messages = Metadata.getAttribute();
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {

            String SHOW_TABLE_STATUS = "show table status from {} where name = '{}';";
            String UPDATE_ENGINE = "ALTER TABLE {} ENGINE = '{}'";

            Map.Entry<String, Metadata> entry = (Map.Entry<String, Metadata>) iter.next();
            Metadata metadata = entry.getValue();
            String tableName = metadata.getTableName();
            jdbc.execute(metadata.getCreateTableSql());

            // 判断储存引擎是被修改
            SHOW_TABLE_STATUS = StringUtils.format(SHOW_TABLE_STATUS, Config.getInstance().getDbname(), tableName);
            String jsonString = jdbc.queryForJson(SHOW_TABLE_STATUS);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);

            if (!String.valueOf(metadata.getEngine()).toUpperCase()
                    .equals(jsonObject.getString("Engine").toUpperCase())) {
                UPDATE_ENGINE = StringUtils.format(UPDATE_ENGINE, tableName, metadata.getEngine());
                jdbc.update(UPDATE_ENGINE);
            }

        }
    }

    /**
     * 检测是否有新增的字段
     *
     * @throws PoseidonException
     */
    public void loadColumn() throws PoseidonException {
        List<Class<?>> models = PoseidonUtils.getModels();
        for (Class<?> target : models) {
            if (SecurityManager.existModel(target)) {
                Model model = PoseidonUtils.getModelAnnotation(target);
                String table = model.value();

                List<String> inDbColumns = jdbc.getColumns(table);
                Metadata metadata = Metadata.getAttribute().get(table);

                Map<String, String> inMessageColumns = metadata.getColumns();
                Iterator iter = inMessageColumns.entrySet().iterator();
                String previousKey = null;
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey();
                    if (!inDbColumns.contains(key)) {
                        String executeScript = StringUtils.format(ADD_COLUMN_SCRIPT, metadata.getTableName(), entry.getValue(), previousKey);
                        jdbc.execute(executeScript);
                    }
                    previousKey = key;
                }
                
            } else {
                throw new PoseidonException("No @Model");
            }
        }
    }

}

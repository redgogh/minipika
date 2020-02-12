package org.laniakeamly.poseidon.framework.model;

import com.alibaba.fastjson.JSONObject;
import org.laniakeamly.poseidon.framework.config.GlobalConfig;
import org.laniakeamly.poseidon.framework.config.PropertiesConfig;
import org.laniakeamly.poseidon.framework.annotation.Model;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.db.JdbcSupport;
import org.laniakeamly.poseidon.framework.exception.PoseidonException;
import org.laniakeamly.poseidon.framework.tools.StringUtils;
import org.laniakeamly.poseidon.framework.tools.POFUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * 初始化时解析Model
 * 其中包括Model中是否存在新增字段，删除字段。
 *
 * init parse model.
 * if model have new column or delete column.
 *
 * Copyright: Create by TianSheng on 2019/11/19 18:05
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 *
 */
public class LoaderModel {

    // 添加字段
    private String ADD_COLUMN_SCRIPT = "ALTER TABLE `{}` ADD {} after `{}`;";
    private String ADD_COLUMN_SCRIPT_PKNULL = "ALTER TABLE `{}` ADD {};";

    private JdbcSupport jdbc = BeansManager.getBean("jdbc");

    /**
     * start
     * @throws PoseidonException
     */
    public void run() {
        try {
            RegularProcessor processor = new RegularProcessor(GlobalConfig.getConfig().getModelPackage());
            processor.modifySetter();
            loadModel();
            loadColumn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析model
     */
    public void loadModel() {
        ParserModel parserModel = new ParserModel();
        parserModel.parse(POFUtils.getModels());
        Map<String, Metadata> messages = Metadata.getAttribute();
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {

            String SHOW_TABLE_STATUS = "show table status from {} where name = '{}'";
            String UPDATE_ENGINE = "ALTER TABLE {} ENGINE = '{}'";

            Map.Entry<String, Metadata> entry = (Map.Entry<String, Metadata>) iter.next();
            Metadata metadata = entry.getValue();
            String tableName = metadata.getTableName();
            jdbc.execute(metadata.getCreateTableSql());

            // 判断储存引擎是被修改
            SHOW_TABLE_STATUS = StringUtils.format(SHOW_TABLE_STATUS, GlobalConfig.getConfig().getDbname(), tableName);
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
        List<Class<?>> models = POFUtils.getModels();
        for (Class<?> target : models) {
            if (SecurityManager.existModel(target)) {
                Model model = POFUtils.getModelAnnotation(target);
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
                        String executeScript;
                        if (!StringUtils.isEmpty(previousKey)) {
                            executeScript = StringUtils.format(ADD_COLUMN_SCRIPT, metadata.getTableName(), entry.getValue(), previousKey);
                        } else {
                            executeScript = StringUtils.format(ADD_COLUMN_SCRIPT_PKNULL, metadata.getTableName(), entry.getValue());
                        }
                        jdbc.execute(executeScript);
                    }
                    previousKey = key;
                }

            } else {
                throw new PoseidonException(target.getName() + " no @Model");
            }
        }
    }

}

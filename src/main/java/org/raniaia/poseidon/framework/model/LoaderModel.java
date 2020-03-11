package org.raniaia.poseidon.framework.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.runtime.ParserException;
import org.raniaia.poseidon.framework.ProvideConstant;
import org.raniaia.poseidon.framework.beans.ContextApplication;
import org.raniaia.poseidon.framework.config.GlobalConfig;
import org.raniaia.poseidon.framework.annotation.model.Model;
import org.raniaia.poseidon.framework.db.JdbcSupport;
import org.raniaia.poseidon.framework.exception.PoseidonException;
import org.raniaia.poseidon.framework.model.database.ColumnModel;
import org.raniaia.poseidon.framework.tools.ModelUtils;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.util.*;

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
    private JdbcSupport jdbc = ContextApplication.getBean("jdbc");

    /**
     * start
     * @throws PoseidonException
     */
    public void run() {
        try {
            ModelPreProcess processor =
                    new ModelPreProcess(GlobalConfig.getConfig().getModelPackage());
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

        Set<String> tables = jdbc.queryForSet(ProvideConstant.QUERY_TABLES, String.class,
                GlobalConfig.getConfig().getDbname());

        ParserModel parserModel = new ParserModel();
        parserModel.parse(ModelUtils.getModels());
        Map<String, Metadata> messages = Metadata.getAttribute();
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {

            String UPDATE_ENGINE = ProvideConstant.UPDATE_ENGINE;
            String SHOW_TABLE_STATUS = ProvideConstant.SHOW_TABLE_STATUS;

            Map.Entry<String, Metadata> entry = (Map.Entry<String, Metadata>) iter.next();
            Metadata metadata = entry.getValue();
            String tableName = metadata.getTableName();
            // 如果数据库中不存在这张表就创建
            if (!tables.contains(tableName)) {
                String modelSimpleName = Metadata.getModelSimpleNameByTable(tableName);
                if (StringUtils.isEmpty(modelSimpleName)) {
                    throw new ParserException("table name not found in '" + tableName + "'");
                }
                // 查询是否存在默认数据
                JSONObject defaultJson = GlobalConfig.getConfig().getDefaultModel();
                JSONArray defaultArray = (JSONArray) defaultJson.get(modelSimpleName);
                List modelDataList = new ArrayList();
                if (defaultArray != null && !defaultArray.isEmpty()) {
                    Iterator iterator = defaultArray.iterator();
                    while (iterator.hasNext()) {
                        JSONObject iterJsonObject = (JSONObject) iterator.next();
                        // 判断数据中是否有特殊变量，如:$currentTime
                        ProvideConstant.updateSpecialVariable(iterJsonObject);
                        modelDataList.add(iterJsonObject.toJavaObject(Metadata.getModelClass(modelSimpleName)));
                    }
                }
                jdbc.execute(metadata.getCreateTableSql());
                // 如果默认数据不为空则添加
                if (!modelDataList.isEmpty()) {
                    jdbc.insert(modelDataList);
                }
            }
            // 保存字段属性
            String queryColumnsSql = StringUtils.format(ProvideConstant.QUERY_COLUMNS, tableName);
            Metadata.putDbColumn(tableName, jdbc.queryForList(queryColumnsSql, ColumnModel.class));

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
        List<Class<?>> models = ModelUtils.getModels();
        for (Class<?> target : models) {
            if (SecurityManager.existModel(target)) {
                Model model = ModelUtils.getModelAnnotation(target);
                String table = model.value();

                List<String> inDbColumns = jdbc.getColumns(table);
                Metadata metadata = Metadata.getAttribute().get(table);

                Map<String, String> inMessageColumns = metadata.getColumns();
                Iterator iter = inMessageColumns.entrySet().iterator();
                String previousKey = null;
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
                    String key = entry.getKey();
                    // 判断数据库中是否存在这个字段
                    if (!inDbColumns.contains(key)) {
                        String executeScript;
                        if (!StringUtils.isEmpty(previousKey)) {
                            executeScript = StringUtils.format(ProvideConstant.ADD_COLUMN_SCRIPT, metadata.getTableName(), entry.getValue(), previousKey);
                        } else {
                            executeScript = StringUtils.format(ProvideConstant.ADD_COLUMN_SCRIPT_PKNULL, metadata.getTableName(), entry.getValue());
                        }
                        jdbc.execute(executeScript);
                    } else {
                        // TODO 如果存在该字段则判断字段属性是否被修改
                    }
                    previousKey = key;
                }

            } else {
                throw new PoseidonException(target.getName() + " no @Model");
            }
        }
    }

}

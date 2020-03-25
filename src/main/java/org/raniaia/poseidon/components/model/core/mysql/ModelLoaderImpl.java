package org.raniaia.poseidon.components.model.core.mysql;

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
 * Creates on 2020/3/20.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jdk.nashorn.internal.runtime.ParserException;
import org.raniaia.poseidon.BeansManager;

import org.raniaia.poseidon.framework.provide.ProvideVar;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.framework.provide.component.Component;
import org.raniaia.poseidon.framework.provide.model.Model;
import org.raniaia.poseidon.components.config.GlobalConfig;
import org.raniaia.poseidon.components.jdbc.JdbcSupport;
import org.raniaia.poseidon.framework.exception.PoseidonException;
import org.raniaia.poseidon.components.model.ModelLoader;
import org.raniaia.poseidon.components.model.ModelParser;
import org.raniaia.poseidon.components.model.publics.Metadata;
import org.raniaia.poseidon.components.model.publics.ModelPreProcess;
import org.raniaia.poseidon.framework.tools.ModelUtils;
import org.raniaia.poseidon.framework.tools.SecurityManager;
import org.raniaia.poseidon.framework.tools.StringUtils;

import java.util.*;

/**
 * Parse the model at initialization, which includes whether the model has new fields
 * or other metadata have changes. like <code>varchar(255) id</code> change to <code>int(10) id</code>.
 *
 * @author tiansheng
 */
@Component
public class ModelLoaderImpl implements ModelLoader {

    @Valid
    private JdbcSupport jdbc;

    @Override
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
    private void loadModel() {

        Set<String> tables = jdbc.queryForSet(ProvideVar.QUERY_TABLES, String.class,
                GlobalConfig.getConfig().getDbname());

        ModelParser parserModel = BeansManager.newInstance(ModelParserImpl.class);
        parserModel.parse(ModelUtils.getModels());
        Map<String, Metadata> messages = Metadata.getAttribute();
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {

            String UPDATE_ENGINE = ProvideVar.UPDATE_ENGINE;
            String SHOW_TABLE_STATUS = ProvideVar.SHOW_TABLE_STATUS;

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
                JSONArray defaultArray = null;
                if (defaultJson != null) {
                    defaultArray = (JSONArray) defaultJson.get(modelSimpleName);
                }
                List modelDataList = new ArrayList();
                if (defaultArray != null && !defaultArray.isEmpty()) {
                    Iterator iterator = defaultArray.iterator();
                    while (iterator.hasNext()) {
                        JSONObject iterJsonObject = (JSONObject) iterator.next();
                        // 判断数据中是否有特殊变量，如:$currentTime
                        ProvideVar.updateSpecialVariable(iterJsonObject);
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
            Metadata.putDbColumn(tableName, jdbc.getColumnMetadata(tableName));

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
    private void loadColumn() throws PoseidonException {
        List<Class<?>> models = ModelUtils.getModels();
        if (models == null) return;
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
                            executeScript = StringUtils.format(ProvideVar.ADD_COLUMN_SCRIPT, metadata.getTableName(), entry.getValue(), previousKey);
                        } else {
                            executeScript = StringUtils.format(ProvideVar.ADD_COLUMN_SCRIPT_PKNULL, metadata.getTableName(), entry.getValue());
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

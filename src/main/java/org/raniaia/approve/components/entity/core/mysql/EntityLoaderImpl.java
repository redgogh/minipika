package org.raniaia.approve.components.entity.core.mysql;

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
import org.raniaia.approve.BeansManager;

import org.raniaia.approve.framework.provide.ProvideVar;
import org.raniaia.approve.framework.provide.Approve;
import org.raniaia.approve.framework.provide.component.Component;
import org.raniaia.approve.framework.provide.entity.Entity;
import org.raniaia.approve.components.config.GlobalConfig;
import org.raniaia.approve.components.jdbc.JdbcSupport;
import org.raniaia.approve.framework.exception.ApproveException;
import org.raniaia.approve.components.entity.EntityLoader;
import org.raniaia.approve.components.entity.EntityParser;
import org.raniaia.approve.components.entity.publics.Metadata;
import org.raniaia.approve.components.entity.publics.EntityPreProcess;
import org.raniaia.approve.framework.tools.EntityUtils;
import org.raniaia.approve.framework.tools.Lists;
import org.raniaia.approve.framework.tools.SecurityManager;
import org.raniaia.approve.framework.tools.StringUtils;

import java.util.*;

/**
 * Parse the entity at initialization, which includes whether the entity has new fields
 * or other metadata have changes. like <code>varchar(255) id</code> change to <code>int(10) id</code>.
 *
 * @author tiansheng
 */
@Component
public class EntityLoaderImpl implements EntityLoader {

    @Approve
    private JdbcSupport jdbc;

    @Override
    public void run() {
        try {
            EntityPreProcess processor =
                    new EntityPreProcess(GlobalConfig.getConfig().getEntityPackage());
            processor.modifySetter();
            loadEntity();
            loadColumn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析entity
     */
    private void loadEntity() {

        Set<String> tables = jdbc.queryForSet(ProvideVar.QUERY_TABLES, String.class,
                GlobalConfig.getConfig().getDbname());

        EntityParser parserEntity = BeansManager.newInstance(org.raniaia.approve.components.entity.core.mysql.EntityParserImpl.class);
        parserEntity.parse(EntityUtils.getEntitys());
        Map<String, Metadata> messages = Metadata.getAttribute();
        for (Map.Entry<String, Metadata> stringMetadataEntry : messages.entrySet()) {

            String UPDATE_ENGINE = ProvideVar.UPDATE_ENGINE;
            String SHOW_TABLE_STATUS = ProvideVar.SHOW_TABLE_STATUS;

            Map.Entry<String, Metadata> entry = stringMetadataEntry;
            Metadata metadata = entry.getValue();
            String tableName = metadata.getTableName();
            // 如果数据库中不存在这张表就创建
            if (!tables.contains(tableName)) {
                String entitySimpleName = Metadata.getEntitySimpleNameByTable(tableName);
                if (StringUtils.isEmpty(entitySimpleName)) {
                    throw new ParserException("table name not found in '" + tableName + "'");
                }
                // 查询是否存在默认数据
                JSONObject defaultJson = GlobalConfig.getConfig().getDefaultEntity();
                JSONArray defaultArray = null;
                if (defaultJson != null) {
                    defaultArray = (JSONArray) defaultJson.get(entitySimpleName);
                }
                List entityDataList = Lists.newArrayList();
                if (defaultArray != null && !defaultArray.isEmpty()) {
                    for (Object o : defaultArray) {
                        JSONObject iterJsonObject = (JSONObject) o;
                        // 判断数据中是否有特殊变量，如:$currentTime
                        ProvideVar.updateSpecialVariable(iterJsonObject);
                        entityDataList.add(iterJsonObject.toJavaObject(Metadata.getEntityClass(entitySimpleName)));
                    }
                }
                jdbc.execute(metadata.getCreateTableSql());
                // 如果默认数据不为空则添加
                if (!entityDataList.isEmpty()) {
                    jdbc.insert(entityDataList);
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
     * @throws ApproveException
     */
    private void loadColumn() throws ApproveException {
        List<Class<?>> entitys = EntityUtils.getEntitys();
        if (entitys == null) return;
        for (Class<?> target : entitys) {
            if (SecurityManager.existEntity(target)) {
                Entity entity = EntityUtils.getEntityAnnotation(target);
                String table = entity.value();

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
                        // TODO 可加可不加的功能，等以后有时间再看到底要不要加，目前就先这样做。
                    }
                    previousKey = key;
                }

            } else {
                throw new ApproveException(target.getName() + " no @Entity");
            }
        }
    }

}

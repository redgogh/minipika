package org.jiakesimk.minipika.framework.provide;

/* ************************************************************************
 *
 * Copyright (C) 2020 tiansheng All rights reserved.
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
 *
 * ************************************************************************/

/*
 * Creates on 2019/12/17.
 */

import com.alibaba.fastjson.JSONObject;
import org.jiakesimk.minipika.components.config.GlobalConfig;
import org.jiakesimk.minipika.framework.tools.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author tiansheng
 * @email jiakesiws@gmail.com
 */
@SuppressWarnings({"unchecked"})
public class ProvideVar {

    public static final String LOG_COMPONENT                            = "Log";
    public static final String LOG_ADAPTER_COMPONENT                    = "LogAdapter";

    // 动态SQL标签
    // --------------------------------------------------------------------------------------------

    public static final String IF                                       = "if";
    public static final String REQ                                      = "$req";
    public static final Object NULL                                     = "null";
    public static final String TEXT                                     = "text";
    public static final String ELSE                                     = "else";
    public static final String ITEM                                     = "item";
    public static final String INDEX                                    = "index";
    public static final String BATCH                                    = "batch";
    public static final String SELECT                                   = "select";
    public static final String INSERT                                   = "insert";
    public static final String UPDATE                                   = "update";
    public static final String DELETE                                   = "delete";
    public static final String COND                                     = "cond";
    public static final String CHOOSE                                   = "choose";
    public static final String FOREACH                                  = "foreach";
    public static final String IF_TEST                                  = "test";
    public static final String PARAMETER                                = "parameter";
    public static final String PARAMS_MAP                               = "map";
    public static final String PARAMS_MAP_GET                           = PARAMS_MAP.concat(".get(\"{}\");");
    public static final String PARAMS_LIST                              = "params";
    public static final String $PARAMETER                               = "$parameter";
    public static final String COLLECTIONS                              = "collections";
    public static final String SQL_PARAMS_SET                           = "params";
    public static final String PARAMS_LIST_ADD                          = PARAMS_LIST.concat(".add({});");
    public static final String CLASS_FULL_NAME                          = "org.raniaia.minipika.$builder.";
    public static final String PARAMETER_SELECT                         = "this:";
    public static final String INSERT_FOREACH_TAG                       = "[infor]:";
    public static final String COND_ATTRIBUTE_KEY                       = "id";
    public static final String PARAMETER_OBJECT_LOCATION                = "org.raniaia.minipika.framework.loader.Parameter";

    // SQL语句
    // --------------------------------------------------------------------------------

    public static final String QUERY_COLUMNS                            = "show full columns from {}";
    public static final String UPDATE_ENGINE                            = "ALTER TABLE {} ENGINE = '{}'";
    public static final String ADD_COLUMN_SCRIPT_PKNULL                 = "ALTER TABLE `{}` ADD {};";
    public static final String ADD_COLUMN_SCRIPT                        = "ALTER TABLE `{}` ADD {} after `{}`;";
    public static final String SHOW_TABLE_STATUS                        = "show table status from {} where name = '{}'";
    public static final String QUERY_TABLES                             = "select table_name from information_schema.tables where table_schema=?";

    // 动态SQL内部代码
    // ---------------------------------------------------------------------

    private static final String SQL_APPEND                              = "sql.append(\" {} \");";
    private static final String GET_MEMBER_VALUE                        = "org.raniaia.minipika.framework.tools.ReflectUtils.getMemberValue({},{})";

    // default_entity.json中的特殊变量
    // ---------------------------------------------------------------------

    private static final String CURRENT_TIME                            = "#currentTime#";

    // ---------------------------------------------------------------------

    /**
     * 更新default_entity JSONObject中的特殊变量
     * @param jsonObject
     */
    public static synchronized void updateSpecialVariable(JSONObject jsonObject) {
        JSONObject parent = GlobalConfig.getConfig().getDefaultEntity();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            // 如果key中的表达式存在#currentTime#
            if (CURRENT_TIME.equals(value)) {
                jsonObject.put(key, new Date());
                // 如果存在this则应该是引用字段
            } else if ("this".equals(value.substring(0, 4))) {
                String dataId = value.split("\\.")[1];
                String dataJsonString = parent.get(dataId).toString();
                jsonObject.put(key, dataJsonString);
            }
        }
    }

    /**
     * 添加sql前的处理方法
     * @param formats
     * @return
     */
    public static final String sqlAppendProcess(String... formats) {
        StringBuilder value = new StringBuilder(StringUtils.format(ProvideVar.SQL_APPEND, formats));
        Pattern pattern = Pattern.compile("\\{\\{(.*?)}}");
        Matcher matcher = pattern.matcher(value);
        while (matcher.find()) {
            String group = matcher.group(1);
            if (!group.contains(".")) {
                group = StringUtils.format(PARAMS_MAP_GET, group);
                group = group.substring(0, group.length() - 1);
            } else {
                group = getMemberValue(group);
            }
            value.append(StringUtils.format(PARAMS_LIST_ADD, group));
        }
        if (value.subSequence(value.length() - 3, value.length() - 2).equals(";")) {
            value.delete(value.length() - 3, value.length() - 2);
        }
        return value.toString().replaceAll("\\{\\{(.*?)}}", "?");
    }

    /**
     * 获取成员值
     * @param value
     * @return
     */
    public static final String getMemberValue(String value) {
        int lastIndex = value.lastIndexOf(".") + 1;
        String last = value.substring(lastIndex);
        String before = value.substring(0, lastIndex - 1);
        return StringUtils.format(GET_MEMBER_VALUE, before, "\"" + last + "\"");
    }

    /**
     * 获取Map Value
     * @param name
     * @return
     */
    public static final String getMapValue(String name) {
        return StringUtils.format(PARAMS_MAP_GET, name);
    }

    public static final String addArrayParameter(List<String> args) {
        StringBuilder createArray = new StringBuilder("new Object[]{");
        for (String arg : args) {
            createArray.append(arg).append(",");
        }
        int len = createArray.length();
        createArray.delete(len - 1, len);
        createArray.append("}");
        return StringUtils.format(ProvideVar.PARAMS_LIST_ADD, createArray.toString());
    }

    public static void main(String[] args) {
        String str = "user.object.name.xxx";
        System.out.println(getMemberValue(str));
    }

}

package org.raniaia.approve.framework.jap;

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
 * Creates on 2020/2/12.
 */

import org.raniaia.available.io.file.Files;
import org.raniaia.approve.components.config.GlobalConfig;
import org.raniaia.approve.framework.exception.ConfigException;
import org.raniaia.approve.framework.tools.ApproveIOUtils;
import org.raniaia.approve.framework.tools.StringUtils;

import java.io.FileInputStream;
import java.util.*;

/**
 *
 * jap 配置文件加载器
 *
 * jap config loader.
 *
 * @author tiansheng
 */
public class JapLoader {

    enum Status {
        NULL, ROOT, CONTENT, KEY,
        VALUE, STRING, ARRAY, COMMENT,
        SINGLE_COMMENT, MULTI_COMMENT
    }

    // 上一个状态
    private Status previous = Status.NULL;

    // 当前状态
    private Status status = Status.NULL;

    private Map<String,Map<String,String>> config = new HashMap<>();

    public Map<String,Map<String,String>> load(){
        return load("resources/approve.jap");
    }

    public Map<String,Map<String,String>> load(String path) {
        String configContent = null;
        if(!GlobalConfig.isJar()) {
            configContent = Files.read(path);
        }else{
            try {
                configContent = ApproveIOUtils.getStringByInputStream(new FileInputStream(System.getProperty("user.dir").concat(path)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 查找root节点有几个
        matchRoots(configContent.replaceAll("// [\\s\\S]*?\n", "")
                .replaceAll("/--[\\s\\S]*?--/", ""));
        return this.config;
    }

    /**
     * 终结符: }、]、\n
     * @param content
     */
    public void matchRoots(String content) {
        int rowLen = 0; // 当前扫描到第几行
        int columnLen = 0; // 当前扫描到第几列
        String key = null;
        String currentRootName = null;
        Map<String, String> properties = new LinkedHashMap<>(); // 用于存放扫描到的键值对
        StringBuilder logger = new StringBuilder(); // 记录当前扫描到哪些内容了，用于报错提示
        StringBuilder builder = new StringBuilder();
        char[] charArray = content.toCharArray();
        for (char v : charArray) {
            logger.append(v);
            // 如果是String
            if (v == '"') {
                builder.append(v);
                if (status == Status.STRING) {
                    if ("\\\\".equals(substringForEnd(builder, 2))) {
                        // 如果前两个为'\\'那么默认就是转义符
                        continue;
                    } else {
                        updateStatus(this.previous);
                        continue;
                    }
                } else {
                    updateStatus(Status.STRING);
                }
                continue;
            }
            if (status == Status.STRING) {
                builder.append(v);
                continue;
            }

            // 如果是数组
            if (v == ']') {
                if (status == Status.ARRAY) {
                    builder.append(v);
                    if (!StringUtils.isEmpty(key)) {
                        properties.put(key, builder.toString());
                    }
                    StringUtils.clear(builder);
                    updateStatus(Status.CONTENT);
                }
                continue;
            }
            if (status == Status.ARRAY) {
                if (v == '\r' || v == '\n' || v == ' ') continue;
                builder.append(v);
                continue;
            }
            if (v == '[') {
                updateStatus(Status.ARRAY);
                builder.append(v);
                continue;
            }
            switch (v) {
                // 如果匹配到分号，就代表找到了一个新的root节点
                case ':': {
                    if (status == Status.VALUE) {
                        builder.append(v);
                        continue;
                    }
                    if (v == ':' && status == Status.NULL) {
                        if (StringUtils.isEmpty(currentRootName)) {
                            currentRootName = builder.toString();
                            StringUtils.clear(builder);
                            updateStatus(Status.ROOT); // 代表当前扫描到ROOT节点了
                            continue;
                        }
                    }
                }
                case '{': {
                    if (status == Status.VALUE) {
                        builder.append(v);
                        continue;
                    }
                    // 如果当前状态为ROOT那么就代表该扫描内容了
                    if (!StringUtils.isEmpty(currentRootName) && status == Status.ROOT) {
                        updateStatus(Status.CONTENT);
                        continue;
                    } else if (status == Status.CONTENT) {
                        builder.append(v);
                        continue;
                    } else {
                        throw new ConfigException("jap syntax '" + v + "' in " + rowLen + ":" + columnLen, logger);
                    }
                }
                // 结束符
                case '}': {
                    status = Status.NULL;
                    previous = Status.NULL;
                    config.put(currentRootName, new HashMap<>(properties));
                    properties.clear();
                    currentRootName = null;
                    continue;
                }
                case '=': {
                    if (status == Status.VALUE) {
                        builder.append(v);
                        continue;
                    }
                    if (status == Status.CONTENT) {
                        key = builder.toString();
                        StringUtils.clear(builder);
                        updateStatus(Status.VALUE);
                        continue;
                    }
                    throw new ConfigException("pconf syntax '" + v + "' in " + rowLen + ":" + columnLen, logger);
                }
                case ' ':
                case '\r': {
                    continue;
                }
                case '\n': {
                    if (status == Status.VALUE) {
                        properties.put(key, builder.toString());
                        StringUtils.clear(builder);
                        updateStatus(Status.CONTENT);
                        key = null;
                        continue;
                    }
                    rowLen++;
                    columnLen = 0;
                    continue;
                }
                default: {
                    builder.append(v);
                }
            }
            columnLen++;
        }
    }

    private void updateStatus(Status status) {
        previous = this.status;
        this.status = status;
    }

    private String substringForEnd(StringBuilder builder, int index) {
        return builder.substring(builder.length() - index, builder.length());
    }

    public static void main(String[] args) {
        new JapLoader().load();
    }

}

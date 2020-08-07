package org.jiakesiws.minipika.components.entity.database;

/* ************************************************************************
 *
 * Copyright (C) 2020 2B键盘 All rights reserved.
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
 * creates on 2020/2/15 1:45
 */

import lombok.Getter;
import lombok.Setter;

/**
 * @author 2B键盘
 * @email jiakesiws@gmail.com
 */
@Getter
@Setter
public class ColumnEntity {

    /**
     * 字段名称
     */
    private String Field;

    /**
     * 字段类型
     */
    private String Type;

    /**
     * Collation代表字符编码。
     * 拥有这个属性的前提是你的字段类型必须是varchar或其他的字符类型。
     */
    private String Collation;

    /**
     * 是否为空
     */
    private String Null;

    /**
     * 键
     */
    private String Key;

    /**
     * 默认数据
     */
    private String Default;

    /**
     * 其他属性，比如auto_increment
     */
    private String Extra;

    /**
     * 权限
     */
    private String Privileges;

    /**
     * 注释
     */
    private String Comment;

}

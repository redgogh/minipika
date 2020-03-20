package org.raniaia.poseidon.components.model.publics;

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
 * Creates on 2020/2/8 16:19
 */

import lombok.Getter;
import lombok.Setter;
import org.raniaia.poseidon.framework.db.JdbcSupport;

import java.lang.reflect.Field;

/**
 *
 * The parent class of model class.
 * @author tiansheng
 */
public abstract class AbstractModel {

    /**
     * Model类是否可以通过校验
     * 如果没有通过校验就不会去进行{@link JdbcSupport#update(Object)}
     * 或{@link JdbcSupport#insert(Object)}等操作
     *
     * Whether the model class can pass the validation.
     * if did not pass then jdbc will not execute {@link JdbcSupport#update(Object)}
     * or {@link JdbcSupport#insert(Object)}
     */
    @Getter
    @Setter
    protected boolean canSave = true;

    /**
     * {@link AbstractModel#canSave}变量的字段对象
     * 负责获取某个Model中canSave的值，因为{@link AbstractModel}是不需要
     * 程序员手动去继承的，框架会自动把{@link AbstractModel}类继承到model类。
     *
     * 所以需要定义这个反射对象去获取canSave的值。
     *
     * Field object of {@link AbstractModel#canSave} it can get the canSave value.
     * because {@link AbstractModel} is no need for programmer extends manually.
     * poseidon framework will auto give all model class extends {@link AbstractModel}.
     *
     * so we need this object to get canSave the value.
     *
     */
    private static Field canSaveField;

    /**
     * 这个构造器会初始化所有Filed对象
     *
     * this construct will initialization all field object.
     * @throws NoSuchFieldException
     */
    protected AbstractModel() throws NoSuchFieldException {
        canSaveField = AbstractModel.class.getDeclaredField("canSave");
        canSaveField.setAccessible(true);
    }

    public static boolean getCanSave(Object o) {
        try {
            return (boolean) canSaveField.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

}

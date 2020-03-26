package org.approve.model;

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



import org.junit.Test;
import org.raniaia.approve.components.model.publics.AbstractModel;
import org.raniaia.approve.components.model.publics.ModelPreProcess;
import org.approve.experiment.TestModel;

/**
 * Create by tiansheng on 2020/2/7 1:56
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class LoaderModel {

    private String[] path = {"org.approve.experiment"};

    @Test
    public void modifyMethod() throws Exception {

        ModelPreProcess processor = new ModelPreProcess(path);
        processor.modifySetter();

        TestModel t = new TestModel();
        t.setEmail("12@aa.com");

        System.out.println(AbstractModel.getCanSave(t));

    }

}

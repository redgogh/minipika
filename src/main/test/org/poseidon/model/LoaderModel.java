package org.poseidon.model;

import org.junit.Test;
import org.raniaia.poseidon.modules.model.publics.AbstractModel;
import org.raniaia.poseidon.modules.model.publics.ModelPreProcess;
import org.poseidon.experiment.TestModel;

/**
 * Create by tiansheng on 2020/2/7 1:56
 *
 * @author tiansheng
 * @version 1.0.0
 * @since 1.8
 */
public class LoaderModel {

    private String[] path = {"org.poseidon.experiment"};

    @Test
    public void modifyMethod() throws Exception {

        ModelPreProcess processor = new ModelPreProcess(path);
        processor.modifySetter();

        TestModel t = new TestModel();
        t.setEmail("12@aa.com");

        System.out.println(AbstractModel.getCanSave(t));

    }

}

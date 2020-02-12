package org.poseidon.model;

import javassist.*;
import org.junit.Test;
import org.laniakeamly.poseidon.framework.model.AbstractModel;
import org.laniakeamly.poseidon.framework.model.RegularProcessor;
import org.laniakeamly.poseidon.framework.tools.POFUtils;
import org.laniakeamly.poseidon.framework.tools.StringUtils;
import org.poseidon.experiment.ProductModel;
import org.poseidon.experiment.TestModel;

import java.lang.reflect.Field;

/**
 * Create by TianSheng on 2020/2/7 1:56
 *
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class LoaderModel {

    private String[] path = {"org.poseidon.experiment"};

    @Test
    public void modifyMethod() throws Exception {

        RegularProcessor processor = new RegularProcessor(path);
        processor.modifySetter();

        TestModel t = new TestModel();
        t.setEmail("12@aa.com");

        System.out.println(AbstractModel.getCanSave(t));

    }

}

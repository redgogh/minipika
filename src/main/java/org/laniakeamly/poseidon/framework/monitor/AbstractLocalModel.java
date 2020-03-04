package org.laniakeamly.poseidon.framework.monitor;

import lombok.Getter;
import lombok.Setter;
import org.laniakeamly.poseidon.framework.annotation.LocalModel;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.tools.ReflectUtils;

/**
 * 本地数据库的实体对象
 *
 * <p/>
 * License: <a href="https://github.com/Laniakeamly/poseidon/blob/master/LICENSE">Apache License 2.0</a>
 * <p/>
 * Copyright: Create by TianSheng on 2019/12/17 18:29
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class AbstractLocalModel {

    protected Database database = BeansManager.getBean("database");

    public AbstractLocalModel(){
        String[] name = ReflectUtils.getMemberName(this);
        LocalModel model = this.getClass().getDeclaredAnnotation(LocalModel.class);
        database.createTable(model.value(),name);
    }

}

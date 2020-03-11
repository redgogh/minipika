package org.raniaia.poseidon.framework.annotation.model;

import java.lang.annotation.*;

/**
 *
 * This annotation only to have {@link Model} annotation the class effective.
 *
 * Model class the column annotation, database column sql just write
 * statement script,no need write column name.
 *
 * If we have a age column now, we just need write sql script 'int(3) not null default 18',
 * as for column name poseidon will auto create.
 *
 * Model class the column property annotation.
 *
 * Copyright: Create by TianSheng on 2019/11/12 10:33
 *
 * @author TianSheng
 * @version 1.0.0
 * @date 2019/11/12 10:33
 * @since 1.8
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    /**
     * 字段声明代码，例如: 'int(1) not null'
     *
     * sql column statement code.
     */
    String value() default "";

}

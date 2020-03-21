package org.raniaia.poseidon.framework.sql.xml;

import org.raniaia.poseidon.BeanManager;
import org.raniaia.poseidon.framework.container.PrecompileContainer;
import org.raniaia.poseidon.components.db.JdbcSupport;
import org.raniaia.poseidon.framework.provide.Valid;
import org.raniaia.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.raniaia.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.*;

/**
 * Copyright: Create by tiansheng on 2019/12/23 11:08
 */
public class SqlMapper {

    private Converter converter = BeanManager.newInstance(Converter.class);
    private PrecompileContainer container = PrecompileContainer.getContainer();

    @Valid
    private JdbcSupport jdbcSupport;

    /**
     * class
     */
    private PrecompiledClass classValue;

    public SqlMapper() {
    }

    public SqlMapper(String name) {
        this.classValue = container.getValue(name);
    }

    public SqlExecute build(String methodName, Parameter parameter) {
        SqlExecute execute = null;
        try {
            Map<String, Object> parameterMap = new HashMap<>();
            parameter.loader(parameterMap);
            PrecompiledMethod precompiledMethod = classValue.getPrecompiledMethod(methodName);
            // 如果没有Method方法就创建一个
            if (precompiledMethod.getIMethod() == null) {
                converter.conversion(precompiledMethod, parameterMap, classValue.getFullName());
            }
            List param = new ArrayList();
            String sql = precompiledMethod.invoke(parameterMap, param);
            execute = new SqlExecute(sql, param.toArray(), precompiledMethod.getResult(),
                    jdbcSupport, precompiledMethod.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execute;
    }

    public static SqlMapper getMapper(String name) {
        SqlMapper mapperBean = BeanManager.get(name);
        if (mapperBean != null) {
            return mapperBean;
        }
        BeanManager.submitBean(name, BeanManager.newInstance(SqlMapper.class,
                new Class[]{String.class}, name));
        return BeanManager.get(name);
    }

}

package org.laniakeamly.poseidon.framework.sql.xml;

import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.container.PrecompileContainer;
import org.laniakeamly.poseidon.framework.db.JdbcSupport;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Create by 2BKeyboard on 2019/12/23 11:08
 */
public class SqlMapper {

    private static Map<String, SqlMapper> mapperMap = new HashMap<>();

    private Converter converter = new Converter();
    private PrecompileContainer container = PrecompileContainer.getContainer();

    private JdbcSupport jdbcSupport = BeansManager.getBean("jdbc");

    /**
     * class
     */
    private PrecompiledClass classValue;

    private SqlMapper() {
    }

    private SqlMapper(String name) {
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
            execute = new SqlExecute(sql, param.toArray(), precompiledMethod.getResult(), jdbcSupport);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execute;
    }

    public static SqlMapper getMapper(String name) {
        if (mapperMap.containsKey(name)) {
            return mapperMap.get(name);
        }
        mapperMap.put(name,new SqlMapper(name));
        return mapperMap.get(name);
    }

}

package org.raniaia.poseidon.framework.sql.xml;

import org.raniaia.poseidon.framework.context.PoseContextApplication;
import org.raniaia.poseidon.framework.container.PrecompileContainer;
import org.raniaia.poseidon.framework.db.JdbcSupport;
import org.raniaia.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.raniaia.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright: Create by tiansheng on 2019/12/23 11:08
 */
public class SqlMapper {

    private Converter converter = new Converter();
    private PrecompileContainer container = PrecompileContainer.getContainer();

    private JdbcSupport jdbcSupport = PoseContextApplication.getBean("jdbc");

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
            execute = new SqlExecute(sql, param.toArray(), precompiledMethod.getResult(),
                    jdbcSupport,precompiledMethod.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return execute;
    }

    public static SqlMapper getMapper(String name) {
        SqlMapper mapperBean = PoseContextApplication.getBean(name);
        if (mapperBean != null) {
            return mapperBean;
        }
        return PoseContextApplication.getBeansManager().putBean(name, new SqlMapper(name));
    }

}

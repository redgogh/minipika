package org.laniakeamly.poseidon.framework.sql;

import lombok.Getter;
import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.compiler.Precompiler;
import org.laniakeamly.poseidon.framework.config.Config;
import org.laniakeamly.poseidon.framework.container.Container;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;
import org.laniakeamly.poseidon.framework.tools.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/19 14:03
 */
public class SqlMapper {

    @Getter
    private String sql;

    @Getter
    private Class<?> result;

    @Getter
    private Object[] args;


    private static final String location = Config.getInstance().getModelPackage();

    private static final Container precompiled = BeansManager.getBean("precompiled");
    private static final Precompiler precompiler = BeansManager.getBean("precompiler");

    public SqlMapper() {
    }

    public SqlMapper(String sql, String result, Object[] args) {
        try {
            this.sql = sql;
            this.args = args;
            if (!StringUtils.isEmpty(result)) {
                this.result = Class.forName(location.concat(".").concat(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建一个sqlMapper
     * @param builderName   xml文件中定义builder标签name属性
     * @param mapperName    mapper标签name属性
     * @param parameters    参数
     * @return
     */
    static SqlMapper builderMapper(String builderName, String mapperName, Map<String, Object> parameters) {
        PrecompiledClass pc = precompiled.getValue(builderName);
        // 判断类是否已经加载到对象
        if (!pc.isLoad()) {
            precompiler.loaderClass(pc);
        }
        PrecompiledMethod pm = precompiler.compilerMethod(pc, mapperName, parameters);
        List args = new LinkedList();
        String sql = "";//pc.getPrecompiledMethod(mapperName).invoke(parameters, args);
        return new SqlMapper(sql, pm.getResult(), args.toArray());
    }

}

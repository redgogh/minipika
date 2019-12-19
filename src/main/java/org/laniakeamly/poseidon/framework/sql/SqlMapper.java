package org.laniakeamly.poseidon.framework.sql;

import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.compiler.Precompiler;
import org.laniakeamly.poseidon.framework.container.Container;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/19 14:03
 */
public class SqlMapper {

    private static Container precompiled = BeansManager.getBean("precompiled");
    private static Precompiler precompiler = BeansManager.getBean("precompiler");

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
        precompiler.compilerMethod(pc, mapperName, parameters);
        List args = new LinkedList();
        pc.getPrecompiledMethod("findUserByName").invoke(parameters,args);
        return null;
    }

}

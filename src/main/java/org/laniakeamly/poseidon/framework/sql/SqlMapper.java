package org.laniakeamly.poseidon.framework.sql;

import org.laniakeamly.poseidon.framework.beans.BeansManager;
import org.laniakeamly.poseidon.framework.container.Container;
import org.laniakeamly.poseidon.framework.container.SqlClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledClass;
import org.laniakeamly.poseidon.framework.sql.xml.build.PrecompiledMethod;

import java.util.Map;

/**
 * Create by 2BKeyboard on 2019/12/19 14:03
 */
public class SqlMapper {

    private static SqlClass sqlClass = BeansManager.getBean("sqlClass");
    private static Container precompiled = BeansManager.getBean("precompiled");

    /**
     * 构建一个sqlMapper
     * @param builderName   xml文件中定义builder标签name属性
     * @param mapperName    mapper标签name属性
     * @param parameters    参数
     * @return
     */
    static SqlMapper builderMapper(String builderName, String mapperName, Map<String,Object> parameters){
        PrecompiledClass pc = precompiled.getValue(builderName);
        PrecompiledMethod pm = pc.getPrecompiledMethod(mapperName);
        // 判断类是否已经加载到对象
        if(!pc.isLoad()){
            sqlClass.loaderClass(pc);
        }
        return null;
    }

}

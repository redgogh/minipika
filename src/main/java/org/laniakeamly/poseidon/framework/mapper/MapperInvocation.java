package org.laniakeamly.poseidon.framework.mapper;

import org.laniakeamly.poseidon.framework.annotation.mapper.*;
import org.laniakeamly.poseidon.framework.beans.PoseidonBeansManager;
import org.laniakeamly.poseidon.framework.sql.xml.SqlExecute;
import org.laniakeamly.poseidon.framework.sql.xml.SqlMapper;
import org.laniakeamly.poseidon.framework.tools.ReflectUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Mapper接口代理类
 * Copyright by TianSheng on 2020/2/28 14:32
 * @author TianSheng
 * @version 1.0.0
 * @since 1.8
 */
public class MapperInvocation implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 如果是一个实现类
        if(Object.class.equals(method.getDeclaringClass())){
            method.invoke(this,args);
        }
        return invocation(method, args);
    }

    /**
     * 创建代理类
     * @param clazz
     * @return
     */
    public static Object invoker(Class<?> clazz){
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new MapperInvocation());
    }

    /**
     * 给接口增加实现类
     * @param method
     * @param args
     * @return
     */
    private Object invocation(Method method,Object[] args){
        // 获取Mapper名称
        String beanName = method.getDeclaringClass().getName();
        String beanSimpleName = method.getDeclaringClass().getSimpleName();
        // 判断容器中是否存在这个SqlMapper对象
        SqlMapper mapper = PoseidonBeansManager.getBean(beanSimpleName);
        if(mapper == null){
            mapper = SqlMapper.getMapper(beanSimpleName);
        }
        //
        // TODO 这段代码有优化空间，SqlExecute可以缓存到BeansManager中。
        // TODO 方便直接获取，而不是每次都去获取Class对象再读取方法参数名进行操作。
        //
        String[] parametersMetadata = ReflectUtils.displayParametersMetadata(beanName,method.getName());
        // 获取sql执行器
        SqlExecute execute = mapper.build(method.getName(), map -> {
            for(int i=0; i<args.length; i++){
                map.put(parametersMetadata[i],args[i]);
            }
        });
        // 获取方法上的注解
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            // 判断这个方法是不是执行的查询
            if(declaredAnnotation instanceof Query){
                Query query = (Query) declaredAnnotation;
                if(query.mode() == QueryMode.DEFAULT) {
                    if(List.class.equals(method.getReturnType())) {
                        return execute.queryForList();
                    }else{
                        return execute.queryForObject();
                    }
                }
                if(query.mode() == QueryMode.LIST) return execute.queryForList();
                if(query.mode() == QueryMode.OBJECT) return execute.queryForObject();
            }
            // 判断这个方法是不是执行的更新
            else if(declaredAnnotation instanceof Update){
                return execute.update();
            }
            // 判断这个方法是不是执行的插入
            else if(declaredAnnotation instanceof Insert){
                return execute.insert();
            }
            // 判断是不是执行的批量
            else if(declaredAnnotation instanceof Execute){
                Execute exe = (Execute) declaredAnnotation;
                if(exe.mode() == ExeMode.BATCH) return execute.executeBatch();
                if(exe.mode() == ExeMode.DEFAULT) return execute.execute();
            }
        }
        return null;
    }

}

package org.laniakeamly.poseidon.framework.mapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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

    private Object invocation(Method method,Object[] args){
        return method.getName();
    }

    public static void main(String[] args) {
        UserMapper mapper = (UserMapper) MapperInvocation.invoker(UserMapper.class);
        System.out.println(mapper.gerUserName());
    }

}

interface UserMapper{
    String gerUserName();
}

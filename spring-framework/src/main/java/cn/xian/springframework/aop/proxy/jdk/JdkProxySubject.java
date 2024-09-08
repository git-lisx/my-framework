package cn.xian.springframework.aop.proxy.jdk;


import cn.xian.log.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lishixian
 * @date 2020/7/11 下午8:58
 */
public class JdkProxySubject {

    /**
     * @param realSubject 真实主体
     * @param <T>         目标主体类
     * @return 该接口的子类的代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T realSubject) {

        // 代理类实现了哪些接口，也可以用这个替代new Class[]{Subject.class}
        Class<?>[] interfaces = realSubject.getClass().getInterfaces();

        InvocationHandler invocationHandler = (Object proxy, Method method, Object[] args) -> {
            Log.info("jdk动态代理......开启事务");
            try {
                Object result = method.invoke(realSubject, args);
                Log.info("jdk动态代理......提交事务");
                return result;
            } catch (Exception e) {
                Log.error(e.getMessage());
                Log.info("jdk动态代理.......事务回滚");
                throw e;
            }
        };
        ClassLoader contextClassLoader = realSubject.getClass().getClassLoader();

        return (T) Proxy.newProxyInstance(contextClassLoader, interfaces, invocationHandler);
    }
}

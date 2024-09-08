package cn.xian.springframework.aop.proxy.cglib;

import cn.xian.log.Log;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 使用 ByteBuddy 实现动态代理
 *
 * @author lishixian
 * @date 2024/9/8 上午8:52
 */
public class ByteBuddyProxySubject {

    /**
     * @param realSubjectClazz 真实主体，目标主体类
     * @param <T>              真实主体，目标主体类
     * @return 真实主体，目标主体类的代理对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> realSubjectClazz) {
        try {
            // 使用 ByteBuddy 创建代理
            return new ByteBuddy()
                    .subclass(realSubjectClazz) // 创建目标类的子类
                    .method(ElementMatchers.any()) // 匹配所有方法
                    .intercept(InvocationHandlerAdapter.of(new InvocationHandler() { // 使用 InvocationHandler 实现方法拦截
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            Log.info("ByteBuddy 动态代理.......开启事务");
                            try {
                                // 执行被代理的方法
                                Object result = method.invoke(proxy, args);
                                Log.info("ByteBuddy 动态代理.......提交事务");
                                return result;
                            } catch (Exception e) {
                                Log.error(e.getMessage());
                                Log.info("ByteBuddy 动态代理.......事务回滚");
                                throw e;
                            }
                        }
                    }))
                    .make()
                    .load(realSubjectClazz.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance(); // 创建代理类的实例
        } catch (Exception e) {
            Log.error("代理创建失败: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

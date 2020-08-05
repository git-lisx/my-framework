package cn.xian.springframework.aop.proxy;

import cn.xian.springframework.aop.proxy.cglib.CglibProxySubject;
import cn.xian.springframework.aop.proxy.jdk.JdkProxySubject;

/**
 * @author lishixian
 * @date 2020/7/11 下午9:17
 */
public class AopProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T createAopProxy(T realSubject) {
        // 创建代理对象
        if (realSubject.getClass().getInterfaces().length != 0) {
            // 基于接口创建代理对象
            return JdkProxySubject.createProxy(realSubject);
        }
        // 基于子类创建代理对象
        return (T) CglibProxySubject.createProxy(realSubject.getClass());
    }

}

package cn.xian.springframework.beans.factory.config;

import cn.xian.springframework.aop.proxy.AopProxyFactory;
import cn.xian.springframework.beans.factory.utils.ClassUtil;
import cn.xian.springframework.transaction.annotation.MyTransactional;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * bean的定义
 *
 * @author lishixian
 * @date 2019/10/15 下午7:58
 */
@Data
public class BeanDefinition {

    /**
     * bean名称
     */
    private String name;
    /**
     * 全路径类名
     */
    private String className;
    /**
     * bean的类型
     */
    private BeanTypeEnum beanType;
    /**
     * aop对象
     */
    private Object aop;
    /**
     * 对象
     */
    private Object bean;


    /**
     * 方法定义的集合
     */
    private List<MethodDefinition> methodDefinitions;

    public BeanDefinition() {
        methodDefinitions = new ArrayList<>();
    }

    public Object getOriginalBean() {
        return aop == null ? bean : aop;
    }

    /**
     * 将class解析成beanDefinition
     *
     * @param clazz 字节码
     * @return BeanDefinition
     */
    public static BeanDefinition parse(Class clazz) {
        BeanDefinition beanDefinition = new BeanDefinition();

        char[] chars = clazz.getSimpleName().toCharArray();
        chars[0] += 32;
        beanDefinition.setName(String.valueOf(chars));
        beanDefinition.setClassName(clazz.getName());
        try {

            List<Annotation> annotations = Arrays.asList(clazz.getAnnotations());
            boolean match = annotations.stream().anyMatch(annotation -> annotation instanceof MyTransactional);
            Object bean = clazz.newInstance();
            if (match) {
                // 创建代理对象
                Object aopProxyBean = AopProxyFactory.createAopProxy(bean);
                beanDefinition.setAop(aopProxyBean);

            }
            beanDefinition.setBean(bean);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        beanDefinition.setMethodDefinitions(invokeMethodDefinition(clazz));
        return beanDefinition;
    }

    /**
     * 将class中的方法解析成MethodDefinition
     *
     * @param clazz 字节码
     * @return 返回所有MethodDefinition
     */
    private static List<MethodDefinition> invokeMethodDefinition(Class clazz) {
        List<MethodDefinition> methodDefinitions = new ArrayList<>();
        Method[] clazzMethods = clazz.getDeclaredMethods();
        for (Method method : clazzMethods) {
            MethodDefinition methodDefinition = new MethodDefinition();
            methodDefinition.setMethod(method);
            methodDefinition.setMethodName(method.getName());
            //获取method的所有参数类型
            Class[] paramsType = method.getParameterTypes();
            methodDefinition.setParamTypes(Arrays.asList(paramsType));
            //获取method的所有参数名称
            List<String> paramNames = ClassUtil.getParamNames(method, clazz);

            methodDefinition.setParamNames(paramNames);
            methodDefinitions.add(methodDefinition);
        }
        return methodDefinitions;
    }


    /**
     * todo 暂未考虑重载的问题
     * 根据方法名获取MethodDefinition
     *
     * @param methodName 方法名
     * @return MethodDefinition
     */
    public MethodDefinition getMethodDefinition(String methodName) {
        Optional<MethodDefinition> methodDefinitionOptional = methodDefinitions.stream()
                .filter(methodDefinition -> methodDefinition.getMethodName().equals(methodName)).findFirst();
        return methodDefinitionOptional.orElse(null);
    }


}

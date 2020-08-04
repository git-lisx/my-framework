package cn.xian.springframework;

import cn.xian.springframework.beans.factory.BeanFactory;
import cn.xian.springframework.beans.factory.config.BeanDefinition;
import cn.xian.springframework.beans.factory.config.MethodDefinition;
import cn.xian.springframework.beans.factory.config.UriMethodRelation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 方法执行器
 *
 * @author lishixian
 * @date 2019/10/17 下午3:54
 */
public class HandlerMapping {


    /**
     * 执行对应的方法
     *
     * @param uriMethodRelation uri与方法的映射对象
     * @param parameterMap      参数列表
     * @return 执行结果
     */
    public static Optional<String> execute(UriMethodRelation uriMethodRelation, Map<String, String[]> parameterMap) {
        // 1.通过 UriMethodRelation，找到 BeanDefinition 和 MethodDefinition
        BeanDefinition beanDefinition = BeanFactory.getInstance().getBeanDefinition(uriMethodRelation.getBeanId());
        MethodDefinition methodDefinition = beanDefinition.getMethodDefinition(uriMethodRelation.getMethodName());

        // 2.参数绑定
        List<String> paramNames = methodDefinition.getParamNames();
        Object[] params = paramNames.stream().map(paramName -> {
            String[] paramValues = parameterMap.get(paramName);
            //暂只支持简单类型的参数绑定
            return paramValues != null ? paramValues[0] : null;
        }).toArray();

        try {
            // 3.通过反射执行对应的方法
            Object result = methodDefinition.getMethod().invoke(beanDefinition.getOriginalBean(), params);
            return result == null ? Optional.empty() : Optional.of(result.toString());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return Optional.of("服务器异常！" + e.getMessage());
        }
    }
}

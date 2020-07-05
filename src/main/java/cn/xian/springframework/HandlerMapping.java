package cn.xian.springframework;

import cn.xian.springframework.beans.factory.BeanFactory;
import cn.xian.springframework.beans.factory.UriFactory;
import cn.xian.springframework.beans.factory.config.BeanDefinition;
import cn.xian.springframework.beans.factory.config.MethodDefinition;
import cn.xian.springframework.beans.factory.config.UriMethodRelation;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
     * 根据uri找到对应的方法并执行
     *
     * @param uri          uri
     * @param parameterMap 参数列表
     * @return 执行结果
     */
    public static String execute(String uri, Map<String, String[]> parameterMap) {
        Optional<UriMethodRelation> uriMethodRelateOptional = UriFactory.instance().getUriMethodRelate(uri);
        if (!uriMethodRelateOptional.isPresent()) {
            return "找不到" + uri + "对应的资源";
        }
        UriMethodRelation uriMethodRelation = uriMethodRelateOptional.get();
        BeanDefinition beanDefinition = BeanFactory.instance().getBeanDefinition(uriMethodRelation.getClassName());
        Object bean = beanDefinition.getBean();
        MethodDefinition methodDefinition = beanDefinition.getMethodDefinition(uriMethodRelation.getMethodName());
        List<Object> params = new ArrayList<>();
        List<String> paramNames = methodDefinition.getParamNames();
        List<Class> paramTypes = methodDefinition.getParamTypes();
        for (String paramName : paramNames) {
            String[] paramValues = parameterMap.get(paramName);
            //暂只支持简单类型的参数绑定
            String paramValue = paramValues != null ? paramValues[0] : null;
            params.add(paramValue);
        }
        try {
            Object result = methodDefinition.getMethod().invoke(bean, params.toArray());
            return result.toString();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return "服务器异常！" + e.getMessage();
        }
    }
}

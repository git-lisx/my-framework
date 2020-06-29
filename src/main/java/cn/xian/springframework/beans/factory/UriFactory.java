package cn.xian.springframework.beans.factory;

import cn.xian.springframework.beans.factory.config.BeanDefinition;
import cn.xian.springframework.beans.factory.config.UriMethodRelate;
import cn.xian.springframework.web.bind.annotation.MyRequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * uri工厂类
 *
 * @author lishixian
 * @date 2019/10/17 上午11:36
 */
public class UriFactory {

    private static volatile UriFactory uriFactory;
    private List<UriMethodRelate> uriMethodRelates;

    public UriFactory() {
        uriMethodRelates = new ArrayList<>();
    }

    /**
     * 获取uri工厂实例
     *
     * @return uri工厂实例
     */
    public static UriFactory instance() {
        if (uriFactory == null) {
            synchronized (UriFactory.class) {
                if (uriFactory == null) {
                    //new操作在jvm内是非原子性的，需加volatile设置内存可见，确保线程安全
                    uriFactory = new UriFactory();
                }
            }
        }
        return uriFactory;
    }


    /**
     * 根据uri获取uri与方法的映射实例
     *
     * @param uri uri
     * @return uri与方法的映射实例
     */
    public Optional<UriMethodRelate> getUriMethodRelate(String uri) {
        List<UriMethodRelate> collect = uriMethodRelates.stream()
                .filter(uriMethodRelate -> uriMethodRelate.getUri().equals(uri)).collect(Collectors.toList());
        if (collect.size() > 1) {
            throw new RuntimeException("uri重复了" + uri);
        }
        if (collect.size() == 1) {
            return Optional.of(collect.get(0));
        }
        return Optional.empty();
    }

    /**
     * 建立uri与方法的映射关系
     */
    public void initUriMapping() {
        List<BeanDefinition> controllerBeanDefinitions = BeanFactory.instance().getControllers();
        for (BeanDefinition controllerBeanDefinition : controllerBeanDefinitions) {
            Class<?> controllerClass = controllerBeanDefinition.getBean().getClass();
            Annotation[] classAnnotations = controllerClass.getDeclaredAnnotations();
            //获取类上的@MyRequestMapping的值
            StringBuilder uriStringBuilder = new StringBuilder("/");
            for (Annotation annotation : classAnnotations) {
                if (annotation instanceof MyRequestMapping) {
                    uriStringBuilder.append(((MyRequestMapping) annotation).value());
                }
            }
            //获取方法上的@MyRequestMapping的值
            Method[] methods = controllerClass.getMethods();
            for (Method method : methods) {
                Annotation[] methodAnnotations = method.getAnnotations();
                for (Annotation methodAnnotation : methodAnnotations) {
                    if (methodAnnotation instanceof MyRequestMapping) {
                        String methodUri = ((MyRequestMapping) methodAnnotation).value();
                        String uri = Paths.get(uriStringBuilder.toString(), methodUri).toString();
                        //暂不考虑重载的情况
                        UriMethodRelate uriMethodRelate
                                = new UriMethodRelate(uri, controllerClass.getName(), method.getName());
                        uriMethodRelates.add(uriMethodRelate);
                    }
                }
            }
        }
    }
}

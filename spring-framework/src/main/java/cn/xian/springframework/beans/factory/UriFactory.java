package cn.xian.springframework.beans.factory;

import cn.xian.springframework.beans.factory.config.BeanDefinition;
import cn.xian.springframework.beans.factory.config.BeanTypeEnum;
import cn.xian.springframework.beans.factory.config.UriMethodRelation;
import cn.xian.springframework.web.bind.annotation.MyRequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * uri工厂类
 *
 * @author lishixian
 * @date 2019/10/17 上午11:36
 */
public class UriFactory {

    /**
     * new操作在jvm内是非原子性的，需加volatile设置禁止指令重排，确保线程安全，
     * 即：在堆内存开辟空间->调用构造方法初始化堆内存数据->将堆内存地址保存到栈内存中，该对象指向堆内存
     */
    private static volatile UriFactory uriFactory;
    private final Map<String, UriMethodRelation> uriMethodRelationMap;

    private UriFactory() {
        uriMethodRelationMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取uri工厂实例
     *
     * @return uri工厂实例
     */
    public static UriFactory getInstance() {
        if (uriFactory == null) {
            synchronized (UriFactory.class) {
                if (uriFactory == null) {
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
    public Optional<UriMethodRelation> getUriMethodRelate(String uri) {
        return uriMethodRelationMap.containsKey(uri) ? Optional.of(uriMethodRelationMap.get(uri)) : Optional.empty();
    }

    /**
     * 建立uri与方法的映射关系
     */
    public void initUriMapping() {
        Set<String> beanIds = BeanFactory.getInstance().getAllBeanIds();
        for (String beanId : beanIds) {
            BeanDefinition beanDefinition = BeanFactory.getInstance().getBeanDefinition(beanId);
            if (!BeanTypeEnum.CONTROLLER.equals(beanDefinition.getBeanType())) {
                continue;
            }

            Class<?> controllerClass = beanDefinition.getOriginalBean().getClass();
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
                        UriMethodRelation uriMethodRelation = new UriMethodRelation(uri, beanId, method.getName());
                        boolean containsKey = uriMethodRelationMap.containsKey(uri);
                        if (containsKey) {
                            throw new RuntimeException("uri重复了，uri:" + uri);
                        }
                        uriMethodRelationMap.put(uri, uriMethodRelation);
                    }
                }
            }
        }
    }
}

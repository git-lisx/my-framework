package cn.xian.springframework.beans.factory;

import cn.xian.springframework.beans.factory.annotation.MyAutowired;
import cn.xian.springframework.beans.factory.classloader.ClassScanner;
import cn.xian.springframework.beans.factory.config.BeanDefinition;
import cn.xian.springframework.beans.factory.config.BeanTypeEnum;
import cn.xian.springframework.stereotype.MyComponent;
import cn.xian.springframework.stereotype.MyController;
import cn.xian.springframework.stereotype.MyRepository;
import cn.xian.springframework.stereotype.MyService;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * bean工厂,使用了设计模式：工厂+单例
 *
 * @author lishixian
 * @date 2019/10/15 下午7:45
 */
@Slf4j
public class BeanFactory {

    /**
     * new操作在jvm内是非原子性的，需加volatile设置禁止指令重排，确保线程安全，
     * 即：在堆内存开辟空间->调用构造方法初始化堆内存数据->将堆内存地址保存到栈内存中，该对象指向堆内存
     */
    private static volatile BeanFactory beanFactory;

    private final Map<String, BeanDefinition> beanDefinitionMap;

    private BeanFactory() {
        beanDefinitionMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取bean工厂
     *
     * @return bean工厂
     */
    public static BeanFactory getInstance() {
        if (beanFactory == null) {
            synchronized (BeanFactory.class) {
                if (beanFactory == null) {
                    beanFactory = new BeanFactory();
                }
            }
        }
        return beanFactory;
    }

    /**
     * 找到拥有@MyAutowired注解的属性，并注入相应的bean
     * 注入依赖
     */
    public void injectDependency() {
        Set<String> beanIds = beanFactory.getAllBeanIds();
        for (String beanId : beanIds) {
            BeanDefinition beanDefinition = BeanFactory.getInstance().getBeanDefinition(beanId);
            Object bean = beanDefinition.getFinalTargetBean();
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof MyAutowired) {
                        String name = field.getName();
                        BeanDefinition fieldBeanDefinition = BeanFactory.getInstance().getBeanDefinition(name);
                        field.setAccessible(true);
                        try {
                            // 依赖注入
                            Object originalBean = fieldBeanDefinition.getFinalTargetBean();
                            field.set(bean, originalBean);
                        } catch (IllegalAccessException | IllegalArgumentException e) {
                            log.warn(e.getMessage(), e);
                        }
                    }
                }
            }

        }
    }

    /**
     * 初始化bean工厂，将当前项目中的class解析成beanDefinition加载beanDefinitionList
     * 只有指定注解的类才会由容器托管
     */
    public void ioc() {
        List<Class<?>> classes = ClassScanner.getClasses();
        for (Class<?> clazz : classes) {
            Annotation[] annotations = clazz.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof MyController) {
                    initBeanDefinitionAndAddFactory(clazz, BeanTypeEnum.CONTROLLER);
                } else if (annotation instanceof MyService) {
                    initBeanDefinitionAndAddFactory(clazz, BeanTypeEnum.SERVICE);
                } else if (annotation instanceof MyRepository) {
                    initBeanDefinitionAndAddFactory(clazz, BeanTypeEnum.REPOSITORY);
                } else if (annotation instanceof MyComponent) {
                    initBeanDefinitionAndAddFactory(clazz, BeanTypeEnum.COMPONENT);
                }
            }
        }
    }

    /**
     * 实例化beanDefinition并添加类型
     *
     * @param clazz    字节码
     * @param beanType bean类型
     */
    private void initBeanDefinitionAndAddFactory(Class<?> clazz, BeanTypeEnum beanType) {
        // 将class解析成beanDefinition
        BeanDefinition beanDefinition = BeanDefinition.parse(clazz, beanType);
        if (beanDefinition != null) {
            boolean containsKey = beanDefinitionMap.containsKey(beanDefinition.getName());
            if (containsKey) {
                throw new RuntimeException("beanId重复了，beanId：" + beanDefinition.getName());
            }
            beanDefinitionMap.put(beanDefinition.getName(), beanDefinition);
        }
    }


    /**
     * 根据类名获取BeanDefinition
     *
     * @param beanId 类名(beanId)
     * @return BeanDefinition
     */
    public BeanDefinition getBeanDefinition(String beanId) {
        return beanDefinitionMap.get(beanId);
    }

    public Set<String> getAllBeanIds() {
        return beanDefinitionMap.keySet();
    }
}

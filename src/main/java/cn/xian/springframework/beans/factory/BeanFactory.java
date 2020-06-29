package cn.xian.springframework.beans.factory;

import cn.xian.springframework.beans.factory.annotation.MyAutowired;
import cn.xian.springframework.beans.factory.classloader.ClassScanner;
import cn.xian.springframework.beans.factory.config.BeanDefinition;
import cn.xian.springframework.beans.factory.config.BeanTypeEnum;
import cn.xian.springframework.stereotype.MyComponent;
import cn.xian.springframework.stereotype.MyController;
import cn.xian.springframework.stereotype.MyRepository;
import cn.xian.springframework.stereotype.MyService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * bean工厂
 *
 * @author lishixian
 * @date 2019/10/15 下午7:45
 */
@Slf4j
@Data
public class BeanFactory {

    private static volatile BeanFactory beanFactory;
    private List<BeanDefinition> beanDefinitions;

    public BeanFactory() {
        beanDefinitions = new ArrayList<>();
    }

    /**
     * 获取bean工厂
     *
     * @return bean工厂
     */
    public static BeanFactory instance() {
        if (beanFactory == null) {
            synchronized (BeanFactory.class) {
                if (beanFactory == null) {
                    //new操作在jvm内是非原子性的，需加volatile设置内存可见，确保线程安全
                    beanFactory = new BeanFactory();
                }
            }
        }
        return beanFactory;
    }

    /**
     * 注入依赖
     */
    public static void injectDependency() {
        List<BeanDefinition> beanDefinitionList = BeanFactory.instance().getBeanDefinitions();
        for (BeanDefinition beanDefinition : beanDefinitionList) {
            Object bean = beanDefinition.getBean();
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof MyAutowired) {
                        String name = field.getName();
                        BeanDefinition fieldBeanDefinition = BeanFactory.instance().getBeanDefinition(name);
                        field.setAccessible(true);
                        try {
                            // 依赖注入
                            field.set(bean, fieldBeanDefinition.getBean());
                        } catch (IllegalAccessException e) {
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
        List<Class> classes = ClassScanner.classes;
        for (Class clazz : classes) {
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
    private void initBeanDefinitionAndAddFactory(Class clazz, BeanTypeEnum beanType) {
        BeanDefinition beanDefinition = BeanDefinition.invoke(clazz);
        if (beanDefinition != null) {
            beanDefinition.setBeanType(beanType);
            beanDefinitions.add(beanDefinition);
        }
    }


    /**
     * 获取工厂里的所有controller的bean定义
     *
     * @return 工厂里的所有controller的bean定义
     */
    public List<BeanDefinition> getControllers() {
        return beanDefinitions.stream()
                .filter(beanDefinition -> BeanTypeEnum.CONTROLLER.equals(beanDefinition.getBeanType()))
                .collect(Collectors.toList());
    }

    /**
     * 根据别名或类名获取BeanDefinition
     *
     * @param nameOrClassName 别名或类名
     * @return BeanDefinition
     */
    public BeanDefinition getBeanDefinition(String nameOrClassName) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            BeanDefinition beanDefinition1 = beanDefinition.getBeanDefinition(nameOrClassName);
            if (beanDefinition1 != null) {
                return beanDefinition1;
            }
        }
        return null;
    }

}

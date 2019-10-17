package cn.xian.springframework.beans.factory;

import cn.xian.springframework.beans.factory.annotation.MyAutowired;
import cn.xian.springframework.beans.factory.classloader.ClassScanner;
import cn.xian.springframework.beans.factory.config.BeanDefinition;
import cn.xian.springframework.beans.factory.config.BeanTypeEnum;
import cn.xian.springframework.stereotype.MyComponent;
import cn.xian.springframework.stereotype.MyController;
import cn.xian.springframework.stereotype.MyRepository;
import cn.xian.springframework.stereotype.MyService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * bean工厂
 *
 * @author lishixian
 * @date 2019/10/15 下午7:45
 */
public class BeanFactory {

    private static BeanFactory beanFactory;
    private List<BeanDefinition> beanDefinitionList = new ArrayList<>();

    /**
     * 获取bean工厂
     *
     * @return bean工厂
     */
    public static BeanFactory instance() {
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
     * 注入依赖
     */
    public static void injectDependency() {
        List<BeanDefinition> beanDefinitionList = BeanFactory.instance().getBeanDefinitionList();
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
                            field.set(bean,fieldBeanDefinition.getBean());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
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
    public void init() {
        List<Class> classes = ClassScanner.classes;
        for (Class clazz : classes) {
            Annotation[] annotations = clazz.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof MyController) {
                    addBeanDefinition(clazz, BeanTypeEnum.CONTROLLER);
                } else if (annotation instanceof MyService) {
                    addBeanDefinition(clazz, BeanTypeEnum.SERVICE);
                } else if (annotation instanceof MyRepository) {
                    addBeanDefinition(clazz, BeanTypeEnum.REPOSITORY);
                } else if (annotation instanceof MyComponent) {
                    addBeanDefinition(clazz, BeanTypeEnum.COMPONENT);
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
    private void addBeanDefinition(Class clazz, BeanTypeEnum beanType) {
        BeanDefinition beanDefinition = BeanDefinition.invoke(clazz);
        if (beanDefinition != null) {
            beanDefinition.setBeanType(beanType);
            beanDefinitionList.add(beanDefinition);
        }
    }

    /**
     * 将beanDefinition添加到工厂里
     *
     * @param beanDefinition bean定义
     */
    public void addBeanDefinitionToFactory(BeanDefinition beanDefinition) {
        beanDefinitionList.add(beanDefinition);
    }

    /**
     * 获取工厂里的所有bean定义
     *
     * @return 工厂里的所有bean定义
     */
    public List<BeanDefinition> getBeanDefinitionList() {
        return beanDefinitionList;
    }

    /**
     * 根据别名或类名获取BeanDefinition
     *
     * @param nameOrClassName 别名或类名
     * @return BeanDefinition
     */
    public BeanDefinition getBeanDefinition(String nameOrClassName) {
        for (BeanDefinition beanDefinition : beanDefinitionList) {
            BeanDefinition beanDefinition1 = beanDefinition.getBeanDefinition(nameOrClassName);
            if (beanDefinition1 != null) {
                return beanDefinition1;
            }
        }
        return null;
    }

}

package cn.xian.springframework.beans.factory.config;

/**
 * bean的种类
 *
 * @author lishixian
 * @date 2019/10/16 下午7:12
 */
public enum BeanTypeEnum {
    /**
     * controller
     */
    CONTROLLER,
    /**
     * service
     */
    SERVICE,
    /**
     * repository
     */
    REPOSITORY,
    /**
     * component
     */
    COMPONENT,
    /***
     * aop
     */
    AOP
}

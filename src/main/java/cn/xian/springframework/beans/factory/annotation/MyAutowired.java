package cn.xian.springframework.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @author lishixian
 * @date 2019/10/16 下午5:46
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {

//    public String value();
}

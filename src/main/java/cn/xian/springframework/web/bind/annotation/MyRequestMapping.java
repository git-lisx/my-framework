package cn.xian.springframework.web.bind.annotation;

import java.lang.annotation.*;

/**
 * @author lishixian
 * @date 2019/10/16 下午5:59
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestMapping {
    String value() default "";
}

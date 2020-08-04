package cn.xian.springframework.transaction.annotation;

import java.lang.annotation.*;

/**
 * @author lishixian
 * @date 2020/7/27 下午5:37
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyTransactional {

}

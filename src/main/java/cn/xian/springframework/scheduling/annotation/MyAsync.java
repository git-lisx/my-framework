package cn.xian.springframework.scheduling.annotation;

import java.lang.annotation.*;

/**
 * @author lishixian
 * @date 2020/7/27 下午2:39
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAsync {


}

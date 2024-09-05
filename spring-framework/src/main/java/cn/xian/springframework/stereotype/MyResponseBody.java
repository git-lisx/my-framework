package cn.xian.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @author lishixian
 * @date 2019/10/16 下午5:30
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyResponseBody {
}

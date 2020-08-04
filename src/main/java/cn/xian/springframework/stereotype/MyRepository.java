package cn.xian.springframework.stereotype;

import java.lang.annotation.*;

/**
 * @author lishixian
 * @date 2019/10/16 下午5:32
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRepository {
}

package cn.xian.webapp.component;

import cn.xian.springframework.stereotype.MyComponent;
import cn.xian.springframework.transaction.annotation.MyTransactional;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lishixian
 * @date 2020/8/4 下午1:59
 */
@MyTransactional
@Slf4j
@MyComponent
public class CglibProxyTransactionComponent {

    public void add(String name) {
        log.info("向数据库A表插入一条数据");
        if (name == null) {
            throw new RuntimeException("程序出异常了");
        }
        log.info("向数据库B表插入一条数据");
    }

}

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
public class TransactionComponent {

    public void add() {
        log.info("向数据库插入一条数据");
    }

}

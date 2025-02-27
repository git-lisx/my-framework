package cn.xian.webapp.component;

import cn.xian.log.Log;
import cn.xian.springframework.stereotype.MyComponent;
import cn.xian.springframework.transaction.annotation.MyTransactional;

/**
 * @author lishixian
 * @date 2020/8/4 下午1:59
 */
@MyTransactional
@MyComponent
public class CglibProxyTransactionComponent {

    public void add(String name) {
        Log.info("向数据库A表插入一条数据");
        if (name == null) {
            throw new RuntimeException("程序出异常了");
        }
        Log.info("向数据库B表插入一条数据");
    }

}

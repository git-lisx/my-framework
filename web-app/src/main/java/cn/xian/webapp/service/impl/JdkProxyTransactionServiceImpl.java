package cn.xian.webapp.service.impl;

import cn.xian.springframework.stereotype.MyService;
import cn.xian.springframework.transaction.annotation.MyTransactional;
import cn.xian.webapp.service.JdkProxyTransactionService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lishixian
 * @date 2020/7/31 上午10:10
 */
@MyTransactional
@Slf4j
@MyService
public class JdkProxyTransactionServiceImpl implements JdkProxyTransactionService {

    @Override
    public void add(String name) {
        log.info("向数据库A表插入一条数据");
        if (name == null) {
            throw new RuntimeException("程序出异常了");
        }
        log.info("向数据库B表插入一条数据");
    }
}

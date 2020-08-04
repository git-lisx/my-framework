package cn.xian.webapp.service.impl;

import cn.xian.springframework.stereotype.MyService;
import cn.xian.springframework.transaction.annotation.MyTransactional;
import cn.xian.webapp.service.TransactionService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lishixian
 * @date 2020/7/31 上午10:10
 */
@MyTransactional
@Slf4j
@MyService
public class TransactionServiceImpl implements TransactionService {

    @Override
    public void add() {
        log.info("向数据库插入一条数据");
    }
}

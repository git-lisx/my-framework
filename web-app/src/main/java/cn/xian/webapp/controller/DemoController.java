package cn.xian.webapp.controller;

import cn.xian.springframework.beans.factory.annotation.MyAutowired;
import cn.xian.springframework.stereotype.MyController;
import cn.xian.springframework.web.bind.annotation.MyRequestMapping;
import cn.xian.webapp.component.CglibProxyTransactionComponent;
import cn.xian.webapp.service.DemoService;
import cn.xian.webapp.service.JdkProxyTransactionService;

/**
 * @author lishixian
 * @date 2019/10/16 下午9:49
 */
@MyController
@MyRequestMapping("/spring")
public class DemoController {

    /**
     * 暂先支持通过byName注入
     */
    @MyAutowired
    private DemoService demoServiceImpl;

    @MyAutowired
    private JdkProxyTransactionService jdkProxyTransactionServiceImpl;
    @MyAutowired
    private CglibProxyTransactionComponent cglibProxyTransactionComponent;

    @MyRequestMapping("/mvc")
    public String mvc(String name) {
        String result = demoServiceImpl.getInfo(name);
        return "返回值为：" + result;
    }

    @MyRequestMapping("/cglib/transaction")
    public String cglibTransaction(String name) {

        cglibProxyTransactionComponent.add(name);

        return "cglib动态代理实现事务管理";
    }


    @MyRequestMapping("/jdk/transaction")
    public String jdkTransaction(String name) {

        jdkProxyTransactionServiceImpl.add(name);

        return "jdk动态代理实现事务管理";
    }

}

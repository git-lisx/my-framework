package cn.xian.webapp.controller;

import cn.xian.springframework.beans.factory.annotation.MyAutowired;
import cn.xian.springframework.stereotype.MyController;
import cn.xian.springframework.web.bind.annotation.MyRequestMapping;
import cn.xian.webapp.service.DemoService;

/**
 * @author lishixian
 * @date 2019/10/16 下午9:49
 */
@MyController
@MyRequestMapping("/demo")
public class DemoController {

    /**
     * 暂先支持通过byName注入
     */
    @MyAutowired
    private DemoService demoServiceImpl;

    @MyRequestMapping("/test")
    public String demoTest(String name) {
        String result = demoServiceImpl.getInfo(name);
        return "返回值为：" + result;
    }


}

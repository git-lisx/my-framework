package cn.xian.webapp.controller;

import cn.xian.springframework.beans.factory.annotation.MyAutowired;
import cn.xian.springframework.stereotype.MyController;
import cn.xian.springframework.web.bind.annotation.MyRequestMapping;
import cn.xian.webapp.service.DemoService;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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


    public static void main(String[] args) {
        List<String> list = Arrays.asList("张三", "李四", "王五");
        int nextInt = new Random().nextInt(list.size());
        System.out.println("恭喜你中奖了：" + list.get(nextInt));
    }

}

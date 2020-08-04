package cn.xian.webapp.service.impl;

import cn.xian.springframework.stereotype.MyService;
import cn.xian.webapp.service.DemoService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author lishixian
 * @date 2019/10/16 下午5:50
 */
@Slf4j
@MyService
public class DemoServiceImpl implements DemoService {
    @Override
    public String getInfo(String name) {

        List<String> list = Arrays.asList("张三", "李四", "王五");

        int nextInt = new Random().nextInt(list.size());
        String luckyViewers = list.get(nextInt);

        log.info("幸运观众：" + luckyViewers);
        return "幸运观众：" + luckyViewers;
    }
}

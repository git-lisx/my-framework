package cn.xian.webapp.service.impl;

import cn.xian.springframework.stereotype.MyService;
import cn.xian.webapp.service.DemoService;

/**
 * @author lishixian
 * @date 2019/10/16 下午5:50
 */
@MyService
public class DemoServiceImpl implements DemoService {
    @Override
    public String getInfo(String name) {
        return "service:" + name;
    }
}

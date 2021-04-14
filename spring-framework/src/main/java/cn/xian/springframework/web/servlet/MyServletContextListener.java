package cn.xian.springframework.web.servlet;

import cn.xian.springframework.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author lishixian
 * @date 2020/8/6 下午7:50
 */
@Slf4j
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

package cn.xian.webapp.listener;

import cn.xian.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author lishixian
 * @date 2020/8/6 下午7:50
 */
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

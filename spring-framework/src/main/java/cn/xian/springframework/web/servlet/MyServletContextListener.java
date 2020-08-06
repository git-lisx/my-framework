package cn.xian.springframework.web.servlet;

import cn.xian.springframework.beans.factory.BeanFactory;
import cn.xian.springframework.beans.factory.UriFactory;
import cn.xian.springframework.beans.factory.classloader.ClassScanner;
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
        //扫描所有class
        log.debug("正在扫描所有class");
        ClassScanner.scan();
        log.debug("class扫描完毕！");

        //初始化bean工厂，装载bean（IOC控制反转）
        BeanFactory.getInstance().ioc();
        log.debug("初始化bean工厂，bean装载完毕！");
        //依赖注入（DI）
        BeanFactory.getInstance().injectDependency();
        log.debug("依赖注入完毕！");

        //建立uri与方法的映射关系
        UriFactory.getInstance().initUriMapping();
        log.debug("uri与方法的映射关系建立完毕！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}

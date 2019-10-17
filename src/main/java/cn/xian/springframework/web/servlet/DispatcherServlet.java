package cn.xian.springframework.web.servlet;

import cn.xian.springframework.beans.factory.BeanFactory;
import cn.xian.springframework.beans.factory.classloader.ClassScanner;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lishixian
 * @date 2019/10/15 下午8:39
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    public static void main(String[] args) throws ServletException {
        DispatcherServlet dispatcherServlet = new DispatcherServlet();
        dispatcherServlet.init();
        System.out.println("执行完毕！");
    }

    @Override
    public void init() throws ServletException {

        //扫描所有class
        ClassScanner.scan();
        log.debug("class扫描完毕！");

        //初始化bean工厂，实例化对象，装载bean（IOC控制反转）
        BeanFactory.instance().init();
        log.debug("初始化bean工厂，bean装载完毕！");
        //依赖注入（DI）
        BeanFactory.injectDependency();
        log.debug("依赖注入完毕！");

        //建立url与方法的映射关系
        log.debug("url与方法的映射关系建立完毕！");


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }
}

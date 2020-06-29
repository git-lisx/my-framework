package cn.xian.springframework.web.servlet;

import cn.xian.springframework.HandlerMapping;
import cn.xian.springframework.beans.factory.BeanFactory;
import cn.xian.springframework.beans.factory.UriFactory;
import cn.xian.springframework.beans.factory.classloader.ClassScanner;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author lishixian
 * @date 2019/10/15 下午8:39
 */
@Slf4j
public class MyDispatcherServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        //扫描所有class
        log.debug("正在扫描所有class");
        ClassScanner.scan();
        log.debug("class扫描完毕！");

        //初始化bean工厂，装载bean（IOC控制反转）
        BeanFactory.instance().ioc();
        log.debug("初始化bean工厂，bean装载完毕！");
        //依赖注入（DI）
        BeanFactory.injectDependency();
        log.debug("依赖注入完毕！");

        //建立uri与方法的映射关系
        UriFactory.instance().initUriMapping();
        log.debug("uri与方法的映射关系建立完毕！");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        String uri = request.getRequestURI();
        Map<String, String[]> parameterMap = request.getParameterMap();
        //根据uri找到对应的方法执行
        String result = HandlerMapping.execute(uri, parameterMap);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(result.getBytes());
            outputStream.flush();
        }
    }
}

package cn.xian.spring.boot.web;

import cn.xian.springframework.context.ApplicationContext;
import cn.xian.springframework.web.servlet.MyDispatcherServlet;
import cn.xian.tomcat.MyTomcat;

import java.io.IOException;

public class MySpringApplication {

    public static void run(Class<?> clazz, String[] args) {
        ApplicationContext.init();

        YamlParser yaml = new YamlParser();
        yaml.loadConfig();
        Integer port = Integer.parseInt(yaml.getConfig("server.port"));
        Integer handleTimeout = Integer.parseInt(yaml.getConfig("server.servlet.async.timeout"));
        Integer sessionTimeout = Integer.parseInt(yaml.getConfig("server.servlet.session.timeout"));

        MyTomcat tomcat = new MyTomcat(port, handleTimeout,sessionTimeout);
        try {
            tomcat.addServlet("/*", new MyDispatcherServlet());
            tomcat.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

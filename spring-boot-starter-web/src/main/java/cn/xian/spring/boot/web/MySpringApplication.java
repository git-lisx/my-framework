package cn.xian.spring.boot.web;

import cn.xian.springframework.context.ApplicationContext;
import cn.xian.springframework.web.servlet.MyDispatcherServlet;
import cn.xian.tomcat.MyTomcat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MySpringApplication {


    public static void main(String[] args) {
        getServerPort();
    }


    public static void run(Class<?> clazz, String[] args) {
        ApplicationContext.init();
        Integer port = getServerPort();
        MyTomcat tomcat = new MyTomcat(port);
        try {
            tomcat.addServlet("/*", new MyDispatcherServlet());
            tomcat.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Integer getServerPort() {
        try (InputStream inputStream = YamlParser.class.getClassLoader().getResourceAsStream("application.yaml")) {
            if (inputStream == null) {
                return null;
            }
            // 解析 YAML 文件
            Map<String, Object> config = YamlParser.parseYaml(inputStream);
            return (Integer) YamlParser.getConfigValue(config, "server.port");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package cn.xian.webapp;

import cn.xian.springframework.web.servlet.MyDispatcherServlet;
import cn.xian.springframework.web.servlet.MyServletContextListener;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class TomcatApplication {

    public static void main(String[] args) throws Exception {
        // 创建 Tomcat 实例
        Tomcat tomcat = new Tomcat();

        // 设置 Tomcat 端口
        tomcat.setPort(8080);

        // 设置 Web 应用的基本目录，比如静态页面的路径
//        Context ctx = tomcat.addWebapp("/", new File("web-app/src/main/resources/static").getAbsolutePath());
        Context ctx = tomcat.addContext("/", null);

        // 手动注册一个 Servlet
        Tomcat.addServlet(ctx, "myServlet", new MyDispatcherServlet());
        ctx.addServletMappingDecoded("/*", "myServlet");

        // 手动注册一个 Listener
        ctx.addApplicationListener(MyServletContextListener.class.getName());

        // 启动 Tomcat
        tomcat.start();

        // 保持 Tomcat 运行
        tomcat.getServer().await();
    }
}

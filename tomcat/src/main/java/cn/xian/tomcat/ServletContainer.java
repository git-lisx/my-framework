package cn.xian.tomcat;

import cn.xian.servlet.http.HttpServletRequest;
import cn.xian.servlet.http.HttpServletResponse;
import cn.xian.servlet.http.MyHttpServlet;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class ServletContainer {
    private static Map<String, MyHttpServlet> servletMapping = new HashMap<>();

    static {
        servletMapping.put("/hello", new MyHttpServlet() {
            @Override
            public void doGet(HttpServletRequest request, HttpServletResponse response) {
                response.setBody("Hello, World!");
                try {
                    response.send();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doPost(HttpServletRequest request, HttpServletResponse response) {
                this.doGet(request, response);
            }
        });
//        servletMapping.put("/hello2", new MyHttpServlet());
    }

    public static MyHttpServlet getServlet(String path) {
        return servletMapping.get(path);
    }
}


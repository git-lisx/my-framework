package cn.xian.tomcat;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

class ServletContainer {
    private static Map<String, MyHttpServlet> servletMapping = new HashMap<>();

    static {
        servletMapping.put("/hello", new MyHttpServlet() {
            @Override
            public void doGet(HttpRequest request, HttpResponse response) {
                response.setBody("Hello, World!");
                try {
                    response.send();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static MyHttpServlet getServlet(String path) {
        return servletMapping.get(path);
    }
}


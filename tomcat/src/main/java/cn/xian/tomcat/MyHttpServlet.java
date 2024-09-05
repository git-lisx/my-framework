package cn.xian.tomcat;

import java.io.IOException;

abstract class MyHttpServlet {
    public void doGet(HttpRequest request, HttpResponse response) {
        response.setBody("Hello, Simple Tomcat!");
        try {
            response.send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

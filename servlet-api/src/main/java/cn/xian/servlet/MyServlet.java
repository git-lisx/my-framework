package cn.xian.servlet;

import cn.xian.servlet.http.MyHttpServletRequest;
import cn.xian.servlet.http.MyHttpServletResponse;

import java.io.IOException;

public interface MyServlet {

    void init();

    void service(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException;

    void destroy();

}

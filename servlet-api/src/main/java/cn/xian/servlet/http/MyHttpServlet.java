package cn.xian.servlet.http;

import java.io.IOException;

public interface MyHttpServlet {


    void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException;


    void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException;

}

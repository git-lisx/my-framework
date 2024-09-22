package cn.xian.servlet.http;

import cn.xian.servlet.MyServlet;

import java.io.IOException;

import static cn.xian.servlet.http.MyHttpServletResponse.SC_METHOD_NOT_ALLOWED;

public abstract class MyHttpServlet implements MyServlet {


    public abstract void doGet(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException;


    public abstract void doPost(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException;


    @Override
    public void init() {

    }

    @Override
    public void service(MyHttpServletRequest request, MyHttpServletResponse response) throws IOException {
        String method = request.getMethod();
        if (method.equals("GET")) {
            this.doGet(request, response);
        } else if (method.equals("POST")) {
            this.doPost(request, response);
        } else {
            response.sendError(SC_METHOD_NOT_ALLOWED, "该请求类型暂不支持");
        }
    }

    @Override
    public void destroy() {

    }

}

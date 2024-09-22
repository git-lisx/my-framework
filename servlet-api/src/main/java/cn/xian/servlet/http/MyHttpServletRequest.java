package cn.xian.servlet.http;

import cn.xian.servlet.MyServletRequest;

public interface MyHttpServletRequest extends MyServletRequest {

    String getMethod();

    String getRequestURI();

    MyHttpSession getSession();

}

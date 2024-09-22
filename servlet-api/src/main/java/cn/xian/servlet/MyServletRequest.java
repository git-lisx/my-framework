package cn.xian.servlet;

import java.util.Map;

public interface MyServletRequest {
    Map<String, String[]> getParameterMap();

}

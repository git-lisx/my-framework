package cn.xian.servlet.http;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class HttpServletRequest {
    private final BufferedReader reader;
    private final String uri;
    private final Map<String, String[]> parameterMap = new HashMap<>();

    public HttpServletRequest(BufferedReader reader, String uri) {
        this.reader = reader;
        this.uri = uri;
    }

    public String getRequestURI() {
        return uri;
    }

    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public void addParameter(String name, String value) {
        parameterMap.put(name, new String[]{value});
    }

    // 这里可以扩展更多的请求解析方法，如获取请求头、参数等
}


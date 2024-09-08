package cn.xian.servlet.http;

import cn.xian.log.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class HttpServletRequest {
    private final BufferedReader reader;
    private String uri;
    private String method;
    private HttpSession session;
    private final Map<String, String[]> parameterMap = new HashMap<>();

    private boolean legal = true;

    public HttpServletRequest(BufferedReader reader) throws IOException {
        this.reader = reader;
//        this.uri = uri;
        init();
    }

    private void init() throws IOException {
        String line;
        List<String> lines = new ArrayList<>();
        // 读取请求头，直到遇到空行，表示请求头的结束
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break;  // 请求头结束
            }
            lines.add(line);  // 保存请求头
        }

        if (lines.isEmpty()) {
            // 为啥会出现这个情况？
            Log.warn("出现null");
            legal = false;
            return;
        }
        // 读取并解析请求头
//            String header;
        String cookies = null;
        for (String header : lines) {
            if (header.startsWith("Cookie:")) {
                cookies = header.substring("Cookie:".length()).trim();
            }
        }
        // 解析Cookie，查找JSESSIONID
        String sessionId = getSessionIdFromCookies(cookies);
        this.session = SessionHandler.getSession(sessionId);

        String[] requestParts = lines.get(0).split(" ");
        this.method = requestParts[0];
        String path = requestParts[1];
        String[] paths = path.split("\\?");
        this.uri = paths[0];
        // 处理参数
        if (paths.length > 1) {
            String[] params = paths[1].split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                String key = keyValue[0];
                String value = keyValue[1];
                addParameter(key, value);
            }
        }
    }

    public String getRequestURI() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public HttpSession getSession() {
        return session;
    }

    public boolean isLegal() {
        return legal;
    }

    public Map<String, String[]> getParameterMap() {
        return parameterMap;
    }

    public void addParameter(String name, String value) {
        parameterMap.put(name, new String[]{value});
    }

    // 解析Cookie字符串，返回SessionID
    private static String getSessionIdFromCookies(String cookies) {
        if (cookies != null) {
            String[] cookieArray = cookies.split(";");
            for (String cookie : cookieArray) {
                String[] keyValue = cookie.split("=");
                if (keyValue.length == 2 && keyValue[0].trim().equals("JSESSIONID")) {
                    return keyValue[1].trim();
                }
            }
        }
        return null;
    }

    // 这里可以扩展更多的请求解析方法，如获取请求头、参数等
}


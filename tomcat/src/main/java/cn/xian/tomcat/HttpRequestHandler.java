package cn.xian.tomcat;

import cn.xian.servlet.http.HttpServletRequest;
import cn.xian.servlet.http.HttpServletResponse;
import cn.xian.servlet.http.HttpSession;
import cn.xian.servlet.http.MyHttpServlet;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 修改 HttpRequestHandler 处理方式
public class HttpRequestHandler implements Runnable {
    private Socket clientSocket;

    private Map<String, MyHttpServlet> servletMap;
//    private final Map<String, HttpSession> sessionMap = new HashMap<>();

    public HttpRequestHandler() {
    }

    public HttpRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public HttpRequestHandler(Socket clientSocket, Map<String, MyHttpServlet> servletMap) {
        this.clientSocket = clientSocket;
        this.servletMap = servletMap;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            HttpServletRequest request = new HttpServletRequest(reader);
            if (!request.isLegal()){
                return;
            }
            HttpSession session = request.getSession();
            String uri = request.getRequestURI();
            String method = request.getMethod();
            HttpServletResponse response = new HttpServletResponse(outputStream,session);
            for (String key : servletMap.keySet()) {
                // 匹配前端控制器
                Matcher matcher = Pattern.compile(key).matcher(uri);
                if (matcher.find()) {
                    MyHttpServlet myHttpServlet = servletMap.get(key);
                    if (method.equals("GET")) {
                        myHttpServlet.doGet(request, response);
                    } else if (method.equals("POST")) {
                        myHttpServlet.doPost(request, response);
                    } else {
                        response.sendError(405, "该请求类型暂不支持");
                    }
                    return;
                }
            }
            response.sendError(404, "该资源未找到");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    // 解析Cookie字符串，返回SessionID
//    private static String getSessionIdFromCookies(String cookies) {
//        if (cookies != null) {
//            String[] cookieArray = cookies.split(";");
//            for (String cookie : cookieArray) {
//                String[] keyValue = cookie.split("=");
//                if (keyValue.length == 2 && keyValue[0].trim().equals("JSESSIONID")) {
//                    return keyValue[1].trim();
//                }
//            }
//        }
//        return null;
//    }
}

package cn.xian.tomcat;

import cn.xian.servlet.http.HttpServletRequest;
import cn.xian.servlet.http.HttpServletResponse;
import cn.xian.servlet.http.MyHttpServlet;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 修改 HttpRequestHandler 处理方式
public class HttpRequestHandler implements Runnable {
    private Socket clientSocket;

    private Map<String, MyHttpServlet> servletMap;

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

//    public Map<String, MyHttpServlet> getServletMap() {
//        return servletMap;
//    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String requestLine = reader.readLine();
            if (requestLine == null) {
                // 为啥会出现这个情况？
                System.out.println("出现null");
                return;
            }
            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];
            String[] paths = path.split("\\?");
            String uri = paths[0];
            HttpServletRequest request = new HttpServletRequest(reader, uri);
            HttpServletResponse response = new HttpServletResponse(outputStream);
            // 处理参数
            if (paths.length > 1) {
                String[] params = paths[1].split("&");
                for (String param : params) {
                   String[] keyValue = param.split("=");
                   String key = keyValue[0];
                   String value = keyValue[1];
                   request.addParameter(key, value);
               }
            }
            for (String key : servletMap.keySet()) {
                // 匹配前端控制器
                Matcher matcher = Pattern.compile(key).matcher(uri);
                if (matcher.find()) {
                    MyHttpServlet myHttpServlet = servletMap.get(key);
                    if (method.equals("GET")) {
                        myHttpServlet.doGet(request, response);
                    } else if (method.equals("POST")) {
                        myHttpServlet.doPost(request, response);
                    }else {
                        response.sendError(405, "该请求类型暂不支持");
                    }
                    return;
                }
            }
            response.sendError(404, "该资源未找到");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

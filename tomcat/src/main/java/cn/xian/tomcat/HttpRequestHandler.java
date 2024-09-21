package cn.xian.tomcat;

import cn.xian.log.Log;
import cn.xian.servlet.http.HttpServletRequest;
import cn.xian.servlet.http.HttpServletResponse;
import cn.xian.servlet.http.HttpSession;
import cn.xian.servlet.http.MyHttpServlet;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 修改 HttpRequestHandler 处理方式
public class HttpRequestHandler implements Runnable {
    private final Socket clientSocket;

    private final Map<String, MyHttpServlet> servletMap;


    public HttpRequestHandler(Socket clientSocket, Map<String, MyHttpServlet> servletMap) {
        this.clientSocket = clientSocket;
        this.servletMap = servletMap;
    }


    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            HttpServletRequest request = new HttpServletRequest(reader);
            if (!request.isLegal()) {
                return;
            }
            HttpSession session = request.getSession();
            HttpServletResponse response = new HttpServletResponse(outputStream, session);
            service(request, response);
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

    private void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Log.info("线程：" + Thread.currentThread().getName() + "开始处理请求");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Log.info("线程：" + Thread.currentThread().getName() + "睡了2秒，现在起来干活啦");


        String uri = request.getRequestURI();
        String method = request.getMethod();
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
    }


}

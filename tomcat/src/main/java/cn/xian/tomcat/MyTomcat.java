package cn.xian.tomcat;

import cn.xian.log.Log;
import cn.xian.servlet.http.MyHttpServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MyTomcat {
    private int port = 8080;
    private final Map<String, MyHttpServlet> servletMap = new HashMap<>();
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

    public MyTomcat(Integer port) {
        if (port != null) {
            this.port = port;
        }

    }

    public void addServlet(String pathRule, MyHttpServlet servlet) {
        servletMap.put(pathRule, servlet);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Log.info("服务已启动，端口号: " + port);
            while (true) {
                // 等待连接
                Socket clientSocket = serverSocket.accept();
                // 启动一个新线程处理请求
                HttpRequestHandler httpRequestHandler = new HttpRequestHandler(clientSocket, servletMap);

                Future<?> future = threadPoolExecutor.submit(httpRequestHandler);
                try {
                    future.get(1, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
//                    future.cancel(true);
                    Log.warn("请求处理超时", e);
                    // TODO 超时响应，待实现
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                new Thread(httpRequestHandler).start();
            }
        }
    }

}

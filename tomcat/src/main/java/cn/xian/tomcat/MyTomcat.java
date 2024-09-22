package cn.xian.tomcat;

import cn.xian.log.Log;
import cn.xian.servlet.http.*;
import cn.xian.tomcat.config.ServerConfig;
import cn.xian.tomcat.http.MyRequest;
import cn.xian.tomcat.http.MyResponse;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MyTomcat {
    private int port = 8080;
    private int handleTimeout = 0;
    private int sessionTimeout = 0;
    private ServerConfig serverConfig;
    private final Map<String, MyHttpServlet> servletMap = new HashMap<>();
    private static final ThreadPoolExecutor threadPoolExecutor =
            new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

    public MyTomcat(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }


    public MyTomcat(Integer port, Integer handleTimeout, Integer sessionTimeout) {
        if (port != null) {
            this.port = port;
            this.handleTimeout = handleTimeout;
            this.sessionTimeout = sessionTimeout;
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
                try (Socket clientSocket = serverSocket.accept();
                     InputStream inputStream = clientSocket.getInputStream();
                     OutputStream outputStream = clientSocket.getOutputStream()) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    MyRequest request = new MyRequest(reader);
                    if (!request.isLegal()) {
                        continue;
                    }
                    MyHttpSession session = request.getSession();
                    MyHttpServletResponse response = new MyResponse(outputStream, session);
                    // 提交至线程池处理请求
                    MyHttpRequestHandler httpRequestHandler = new MyHttpRequestHandler(servletMap, request, response);
                    Future<?> future = threadPoolExecutor.submit(httpRequestHandler);
                    try {
                        if (handleTimeout == 0) {
                            future.get();
                        } else {
                            future.get(handleTimeout, TimeUnit.SECONDS);
                        }
                    } catch (TimeoutException e) {
                        Log.warn("请求处理超时，响应客户端超时");
                        httpRequestHandler.timeOutService();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

}

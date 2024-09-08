package cn.xian.tomcat;

import cn.xian.servlet.http.MyHttpServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class MyTomcat {
    private int port = 8080;
    private final HttpRequestHandler httpRequestHandler;
    private final Map<String, MyHttpServlet> servletMap = new HashMap<>();

    public MyTomcat(Integer port) {
        if (port != null) {
            this.port = port;
        }
        httpRequestHandler = new HttpRequestHandler();

    }

    public void addServlet(String pathRule, MyHttpServlet servlet) {
        servletMap.put(pathRule, servlet);
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("SimpleTomcat started on port: " + port);
            while (true) {
                // 等待连接
                Socket clientSocket = serverSocket.accept();
                // 启动一个新线程处理请求
                new Thread(new HttpRequestHandler(clientSocket,servletMap)).start();
//                httpRequestHandler.setClientSocket(clientSocket);
//                new Thread(httpRequestHandler).start();
                System.out.println("处理完成一个请求");
            }
        }
    }

}

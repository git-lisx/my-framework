package cn.xian.tomcat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTomcat {
    private int port = 8080;

    public SimpleTomcat(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("SimpleTomcat started on port: " + port);

        while (true) {
            // 等待连接
            Socket clientSocket = serverSocket.accept();
            // 启动一个新线程处理请求
            new Thread(new HttpRequestHandler(clientSocket)).start();
            System.out.println("处理完成一个请求");
        }

    }

    public static void main(String[] args) throws IOException {
        SimpleTomcat tomcat = new SimpleTomcat(8080);
        tomcat.start();
    }
}

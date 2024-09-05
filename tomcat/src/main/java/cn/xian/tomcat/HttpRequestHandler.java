package cn.xian.tomcat;

import java.io.*;
import java.net.Socket;

// 修改 HttpRequestHandler 处理方式
class HttpRequestHandler implements Runnable {
    private Socket clientSocket;

    public HttpRequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream()) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String requestLine = reader.readLine();
            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];

            // 从 Servlet 容器中获取对应的 Servlet
            MyHttpServlet servlet = ServletContainer.getServlet(path);
            if (servlet != null) {
                servlet.doGet(new HttpRequest(reader), new HttpResponse(outputStream));
            } else {
                HttpResponse response = new HttpResponse(outputStream);
                response.setStatusCode(404);
                response.setBody("Not Found");
                response.send();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

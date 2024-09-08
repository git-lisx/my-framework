package cn.xian.servlet.http;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class HttpServletResponse {
    private final OutputStream outputStream;
    private int statusCode = 200;
    private String body = "";

    public HttpServletResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void send() throws IOException {
        PrintWriter writer = new PrintWriter(outputStream);
        writer.println("HTTP/1.1 " + statusCode + " " + getReasonPhrase(statusCode));
        writer.println("Content-Type: text/html; charset=UTF-8");
//        writer.println("Content-Type: application/json; charset=UTF-8");
        writer.println("Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length);
        writer.println();
        writer.println(body);
        writer.flush();
    }

    private String getReasonPhrase(int statusCode) {
        switch (statusCode) {
            case 200:
                return "OK";
            case 404:
                return "Not Found";
            default:
                return "";
        }
    }

    public void sendError(int statusCode,String body) throws IOException {
        this.statusCode = statusCode;
        this.body = body;
        this.send();
    }

}

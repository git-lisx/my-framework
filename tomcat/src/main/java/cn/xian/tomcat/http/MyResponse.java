package cn.xian.tomcat.http;

import cn.xian.servlet.http.MyHttpServletResponse;
import cn.xian.servlet.http.MyHttpSession;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class MyResponse implements MyHttpServletResponse {
    private final OutputStream outputStream;
    private int statusCode = SC_OK;
    private String body = "";
    private final MyHttpSession session;
    private final PrintWriter writer;

    public MyResponse(OutputStream outputStream, MyHttpSession session) {
        this.outputStream = outputStream;
        this.writer = new PrintWriter(outputStream);
        this.session = session;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void send() throws IOException {
        writer.println("HTTP/1.1 " + statusCode + " " + getReasonPhrase(statusCode));
        writer.println("Content-Type: text/html; charset=UTF-8");
//        writer.println("Content-Type: application/json; charset=UTF-8");
        writer.println("Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length);
        String sessionId = session.getId();
        writer.println("Set-Cookie: JSESSIONID=" + sessionId);

        writer.println();
        writer.println(body);
        writer.flush();
    }

    private String getReasonPhrase(int statusCode) {
        switch (statusCode) {
            case SC_OK:
                return "OK";
            case SC_NOT_FOUND:
                return "Not Found";
            default:
                return "";
        }
    }

    public void sendError(int status, String msg) throws IOException {
        this.statusCode = status;
        this.body = msg;
        this.send();
    }

    @Override
    public void sendError(int status) throws IOException {
        sendError(status, "");
    }

    @Override
    public void setStatus(int status) {
        this.statusCode = status;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
    }
}

package cn.xian.tomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

class HttpRequest {
    private BufferedReader reader;

    public HttpRequest(BufferedReader reader) {
        this.reader = reader;
    }

    // 这里可以扩展更多的请求解析方法，如获取请求头、参数等
}


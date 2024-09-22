package cn.xian.servlet;

import java.io.IOException;
import java.io.PrintWriter;

public interface MyServletResponse {

    PrintWriter getWriter() throws IOException;

    void flushBuffer() throws IOException;

}

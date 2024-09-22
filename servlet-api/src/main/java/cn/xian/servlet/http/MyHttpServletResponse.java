package cn.xian.servlet.http;

import cn.xian.servlet.MyServletResponse;

import java.io.IOException;

public interface MyHttpServletResponse extends MyServletResponse {

    void setStatus(int status);

    void sendError(int status, String msg) throws IOException;

    void sendError(int status) throws IOException;

    /**
     * 为了方便实现，临时接口，标准的servlet没有该接口
     *
     * @throws IOException 异常
     */
    @Deprecated
    void send() throws IOException;

    /**
     * 为了方便实现，临时接口，标准的servlet没有该接口
     *
     * @param body 响应体
     */
    @Deprecated
    void setBody(String body);

    /**
     * 正常的响应状态码
     */
    int SC_OK = 200;
    /**
     * 资源未找到
     */
    int SC_NOT_FOUND = 404;
    /**
     * 方法不允许
     */
    int SC_METHOD_NOT_ALLOWED = 405;
    /**
     * 请求超时
     */
    int SC_REQUEST_TIMEOUT = 408;

}

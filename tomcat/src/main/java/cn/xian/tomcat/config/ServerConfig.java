package cn.xian.tomcat.config;

public class ServerConfig {

    /**
     * 服务器端口号
     */
    private int port = 8080;
    /**
     * 处理超时时间，单位：秒
     */
    private int handleTimeout = 0;
    /**
     * 会话超时时间，单位：分钟
     */
    private int sessionTimeout = 0;

    public ServerConfig(int port, int handleTimeout, int sessionTimeout) {
        this.port = port;
        this.handleTimeout = handleTimeout;
        this.sessionTimeout = sessionTimeout;
    }


    public int getPort() {
        return port;
    }

    public int getHandleTimeout() {
        return handleTimeout;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }
}

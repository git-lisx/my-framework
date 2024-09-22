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


    public static ServerConfig getInstance(){
        return new ServerConfig();
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

package cn.xian.tomcat;

import cn.xian.tomcat.config.ServerConfig;

public class MyContext {


    private static volatile ServerConfig serverConfig;


    public static void setServerConfig(ServerConfig config) {
        serverConfig = config;
    }
    public static ServerConfig getServerConfig() {
        return serverConfig;
    }

}

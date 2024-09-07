package cn.xian.spring.boot.web;

import cn.xian.tomcat.SimpleTomcat;

import java.io.IOException;
public class MySpringApplication {



    public static void run(Class<?> clazz, String[] args){
        SimpleTomcat tomcat = new SimpleTomcat(8080);
        try {
            tomcat.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

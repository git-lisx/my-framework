package cn.xian.tomcat;

import java.io.IOException;

public class SimpleTomcatTest {

    public static void main(String[] args) throws IOException {
        MyTomcat tomcat = new MyTomcat(8080,0, 0);
        tomcat.start();
    }
}

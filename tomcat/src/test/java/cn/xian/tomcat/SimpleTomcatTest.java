package cn.xian.tomcat;

import java.io.IOException;

public class SimpleTomcatTest {

    public static void main(String[] args) throws IOException {
        SimpleTomcat tomcat = new SimpleTomcat(8080);
        tomcat.start();
    }
}

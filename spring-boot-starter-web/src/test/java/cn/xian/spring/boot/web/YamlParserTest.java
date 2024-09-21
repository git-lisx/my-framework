//package cn.xian.spring.boot.web;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//
//import static cn.xian.spring.boot.web.YamlParser.getConfigValue;
//import static cn.xian.spring.boot.web.YamlParser.parseYaml;
//
//public class YamlParserTest {
//
//    public static void main(String[] args) {
//        try (InputStream inputStream = YamlParser.class.getClassLoader().getResourceAsStream("config.yaml")) {
//            if (inputStream == null) {
//                throw new IllegalArgumentException("YAML file not found!");
//            }
//
//            // 解析 YAML 文件
//            Map<String, Object> config = parseYaml(inputStream);
//
//            // 获取具体配置
//            System.out.println("Server Name: " + getConfigValue(config, "server.name"));
//            System.out.println("Server Port: " + getConfigValue(config, "server.port"));
//            System.out.println("Database Host: " + getConfigValue(config, "database.host"));
//            System.out.println("Database Port: " + getConfigValue(config, "database.port"));
//            System.out.println("Database Username: " + getConfigValue(config, "database.username"));
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}

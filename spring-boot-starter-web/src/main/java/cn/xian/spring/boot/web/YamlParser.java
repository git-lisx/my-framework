package cn.xian.spring.boot.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class YamlParser {

    // 解析 YAML 文件
    public static Map<String, Object> parseYaml(InputStream inputStream) throws IOException {
        Map<String, Object> yamlMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            String currentSection = null;  // 用于保存当前的 section (如 server, database)

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // 忽略空行或注释
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // 处理 section (例如：server:)
                if (line.endsWith(":") && !line.startsWith(" ")) {
                    currentSection = line.substring(0, line.length() - 1);
                    yamlMap.put(currentSection, new HashMap<String, Object>());
                } else if (currentSection != null && line.contains(":")) {
                    // 处理键值对 (例如：port: 8080)
                    String[] parts = line.split(":", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    // 将键值对放入当前 section 的 map 中
                    Map<String, Object> sectionMap = (Map<String, Object>) yamlMap.get(currentSection);
                    sectionMap.put(key, parseValue(value));
                }
            }
        }
        return yamlMap;
    }

    // 解析值，将字符串值转换为适当的类型
    private static Object parseValue(String value) {
        if (value.matches("\\d+")) {
            return Integer.parseInt(value);  // 解析整数
        } else if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);  // 解析布尔值
        }
        return value;  // 默认返回字符串
    }
    // 新增方法：通过路径获取配置值
    public static Object getConfigValue(Map<String, Object> config, String path) {
        String[] keys = path.split("\\.");
        Object value = config;

        for (String key : keys) {
            if (value instanceof Map) {
                value = ((Map<?, ?>) value).get(key);
            } else {
                return null; // 路径错误时返回 null
            }
        }

        return value;
    }

}

package cn.xian.spring.boot.web;

import cn.xian.log.Log;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class YamlParser {


    private final Map<String, String> configMap = new HashMap<>();

    // 读取 YAML 文件并解析
    public void loadConfig() {
        try (InputStream inputStream = YamlParser.class.getClassLoader().getResourceAsStream("application.yaml");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            String currentPrefix = "";
            int lastIndentLevel = 0;

            while ((line = reader.readLine()) != null) {
                // 过滤注释
                line = line.split("#")[0];

                // 跳过空行
                if (line.isEmpty()) {
                    continue;
                }

                // 计算当前行的缩进
                int indentLevel = getIndentLevel(line);

                // 移除缩进后的行
                line = line.trim();

                // 处理键值对
                if (line.contains(":")) {
                    String[] parts = line.split(":", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    // 处理嵌套结构
                    if (value.isEmpty()) {
                        currentPrefix = updatePrefix(currentPrefix, key, indentLevel, lastIndentLevel);
                        lastIndentLevel = indentLevel;
                    } else {
                        // 构建完整键名，并存储值
                        String fullKey = currentPrefix + key;
                        configMap.put(fullKey, value.replace("#", "").trim());
                    }
                }
            }
        } catch (IOException e) {
            Log.warn("读取配置文件失败", e);
        }
    }

    // 获取缩进层次
    private int getIndentLevel(String line) {
        int indentLevel = 0;
        for (char c : line.toCharArray()) {
            if (c == ' ') {
                indentLevel++;
            } else {
                break;
            }
        }
        return indentLevel / 2; // 假设每级缩进是2个空格
    }

    // 更新键前缀
    private String updatePrefix(String currentPrefix, String key, int indentLevel, int lastIndentLevel) {
        String[] parts = currentPrefix.split("\\.");
        StringBuilder newPrefix = new StringBuilder();

        // 如果缩进级别减少了，修正当前前缀
        for (int i = 0; i < indentLevel; i++) {
            if (i < parts.length) {
                newPrefix.append(parts[i]).append(".");
            }
        }

        // 添加当前键
        newPrefix.append(key).append(".");
        return newPrefix.toString();
    }

    // 根据键名获取配置值
    public String getConfig(String key) {
        if (configMap.containsKey(key)) {
            String value = configMap.get(key);
            Log.info("获取配置值：" + key + "=" + value);
            return value;
        } else {
            Log.error("配置不存在: " + key);
            throw new RuntimeException("配置不存在: " + key);
        }
    }

}

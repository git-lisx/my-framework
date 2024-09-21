package cn.xian.servlet.http;

import java.util.Map;

public class MyHttpSession {
    private String sessionId;

    private long lastAccessTime;

    private Map<String, Object> attributes;

    public MyHttpSession(String sessionId, Map<String, Object> attributes) {
        this.sessionId = sessionId;
        this.attributes = attributes;
        this.lastAccessTime = System.currentTimeMillis();
    }

    public void refreshLastAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }
}

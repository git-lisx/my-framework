package cn.xian.servlet.http;

import java.util.Map;

public class HttpSession {
    private String sessionId;

    private Map<String, Object> attributes;

    public HttpSession(String sessionId, Map<String, Object> attributes) {
        this.sessionId = sessionId;
        this.attributes = attributes;
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
}

package cn.xian.tomcat.http;

import cn.xian.servlet.http.MyHttpSession;
import cn.xian.tomcat.MyContext;

import java.util.HashMap;
import java.util.Map;

public class MyStandardSession implements MyHttpSession {
    private String sessionId;
    private long creationTime;
    private long lastAccessTime;
    /**
     * 最大失效时间，小于等于0代表永不失效
     */
    private int maxInactiveInterval = -1;
    /**
     * 是否已失效
     */
    private boolean isValid = false;

    private Map<String, Object> attributes;

    public MyStandardSession(String sessionId) {
        this.sessionId = sessionId;
        this.attributes = new HashMap<>();
        this.creationTime = System.currentTimeMillis();
        this.lastAccessTime = creationTime;
        this.maxInactiveInterval = MyContext.getServerConfig().getSessionTimeout();
    }

    public void refreshLastAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }


    @Override
    public String getId() {
        return this.sessionId;
    }

    @Override
    public void setAttribute(String name, Object value) {
        this.attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return this.attributes.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        this.attributes.remove(name);
    }

    @Override
    public void invalidate() {
        this.isValid = true;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessTime;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public int getMaxInactiveInterval() {
        return maxInactiveInterval;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {
        maxInactiveInterval = interval;
    }
}

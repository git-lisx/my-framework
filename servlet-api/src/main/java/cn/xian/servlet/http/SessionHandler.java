package cn.xian.servlet.http;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionHandler {
    private static final Map<String, HttpSession> sessionMap = new HashMap<>();

    public static HttpSession getSession(String sessionId) {
        HttpSession session ;
        if (sessionId == null || !sessionMap.containsKey(sessionId)) {
            // 如果没有Session，创建一个新的Session
            sessionId = UUID.randomUUID().toString();
            session = new HttpSession(sessionId, new HashMap<>());
            sessionMap.put(sessionId, session);
            System.out.println("创建新session：" + sessionId);
        } else {
            session = sessionMap.get(sessionId);
            System.out.println("session已存在" + sessionId);
        }
        return session;
    }
}

package cn.xian.servlet.http;

import cn.xian.log.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MySessionHandler {
    private static final Map<String, MyHttpSession> sessionMap = new HashMap<>();

    public static MyHttpSession getSession(String sessionId) {

        MyHttpSession session;
        if (sessionId == null || !sessionMap.containsKey(sessionId)) {
            // 如果没有Session，创建一个新的Session
            sessionId = UUID.randomUUID().toString();
            session = new MyHttpSession(sessionId, new HashMap<>());
            sessionMap.put(sessionId, session);
            Log.info("创建新session：" + sessionId);
        } else {
            session = sessionMap.get(sessionId);
            // 30秒内未访问，则创建新session
            if ((System.currentTimeMillis() - session.getLastAccessTime()) / 1000 > 30) {
                sessionMap.remove(sessionId);
                session = new MyHttpSession(sessionId, new HashMap<>());
                sessionMap.put(sessionId, session);
                Log.info("session已过期，创建新session：" + sessionId);
            } else {
                session.refreshLastAccessTime();
                Log.info("session已存在" + sessionId);
            }
        }

        return session;
    }
}

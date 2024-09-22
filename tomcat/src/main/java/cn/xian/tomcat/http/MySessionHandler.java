package cn.xian.tomcat.http;

import cn.xian.log.Log;
import cn.xian.servlet.http.MyHttpSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MySessionHandler {
    private static final Map<String, MyStandardSession> sessionMap = new HashMap<>();

    public static MyHttpSession getSession(String sessionId) {

        MyStandardSession session;
        if (sessionId == null || !sessionMap.containsKey(sessionId)) {
            // 如果没有Session，创建一个新的Session
            sessionId = UUID.randomUUID().toString();
            session = new MyStandardSession(sessionId);
            sessionMap.put(sessionId, session);
            Log.info("创建新session：" + sessionId);
        } else {
            session = sessionMap.get(sessionId);
            // 30秒内未访问，则创建新session
            // 单位秒
            int interval = session.getMaxInactiveInterval();
            if (interval > 0 && (System.currentTimeMillis() - session.getLastAccessedTime()) / 1000 > 30) {
                sessionMap.remove(sessionId);
                session = new MyStandardSession(sessionId);
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

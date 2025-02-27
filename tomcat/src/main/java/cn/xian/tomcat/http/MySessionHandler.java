package cn.xian.tomcat.http;

import cn.xian.log.Log;
import cn.xian.servlet.http.MyHttpSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MySessionHandler {
    private static final Map<String, MyStandardSession> sessionMap = new ConcurrentHashMap<>();

    static {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Set<String> sessionIds = sessionMap.keySet();
                for (String sessionId : sessionIds) {
                    MyStandardSession session = sessionMap.get(sessionId);
                    if (session != null) {
                        // 单位秒
                        long intervalTime = (System.currentTimeMillis() - session.getLastAccessedTime()) / 1000;
                        Log.debug("sessionId=" + sessionId + " 距离最近使用已过去：" + intervalTime + " 秒");
                        int interval = session.getMaxInactiveInterval();
                        if (interval > 0 && intervalTime > interval) {
                            // interval秒内未访问，则清除
                            sessionMap.remove(sessionId);
                            Log.info("sessionId=" + sessionId + " 已过期，清理session");
                        }
                    }
                }
            }
        };
        // 每隔 1 秒执行一次任务
        timer.schedule(task, 0, 1000);
        Log.info("启动session监控任务");
    }

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
            // 单位秒
            int interval = session.getMaxInactiveInterval();
            if (interval > 0 && (System.currentTimeMillis() - session.getLastAccessedTime()) / 1000 > interval) {
                // interval秒内未访问，则创建新session
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

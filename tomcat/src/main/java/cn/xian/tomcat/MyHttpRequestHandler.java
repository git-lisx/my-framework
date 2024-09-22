package cn.xian.tomcat;

import cn.xian.log.Log;
import cn.xian.servlet.http.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.xian.servlet.http.MyHttpServletResponse.SC_NOT_FOUND;
import static cn.xian.servlet.http.MyHttpServletResponse.SC_REQUEST_TIMEOUT;

public class MyHttpRequestHandler implements Runnable {

    private final Map<String, MyHttpServlet> servletMap;

    private final MyHttpServletRequest request;
    private final MyHttpServletResponse response;


    public MyHttpRequestHandler(Map<String, MyHttpServlet> servletMap, MyHttpServletRequest request, MyHttpServletResponse response) {
        this.servletMap = servletMap;
        this.request = request;
        this.response = response;
    }

    @Override
    public void run() {
        try {
            Log.info("线程：" + Thread.currentThread().getName() + "开始处理请求");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Log.info("线程：" + Thread.currentThread().getName() + "睡了2秒，现在起来干活啦");

            String uri = request.getRequestURI();
            for (String key : servletMap.keySet()) {
                // 匹配前端控制器
                Matcher matcher = Pattern.compile(key).matcher(uri);
                if (!matcher.find()) {
                    Log.warn("该URI没有相应的前端控制器: {}", uri);
                    response.sendError(SC_NOT_FOUND, "该资源未找到");
                }
                MyHttpServlet servlet = servletMap.get(key);
                servlet.service(request, response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void timeOutService() {
        response.setStatus(SC_REQUEST_TIMEOUT);
        try {
            response.send();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}

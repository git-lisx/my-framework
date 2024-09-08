package cn.xian.springframework.web.servlet;

import cn.xian.log.Log;
import cn.xian.servlet.http.HttpServletRequest;
import cn.xian.servlet.http.HttpServletResponse;
import cn.xian.servlet.http.MyHttpServlet;
import cn.xian.springframework.HandlerMapping;
import cn.xian.springframework.beans.factory.UriFactory;
import cn.xian.springframework.beans.factory.config.UriMethodRelation;

//import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class MyDispatcherServlet implements MyHttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//        response.setContentType(TEXT_HTML_UTF_8);
//        response.setContentType(APPLICATION_JSON_UTF_8);

        String uri = request.getRequestURI();
        Log.debug("Received request for URI: {}", uri);

        // 根据 URI 查找对应的方法执行
        Optional<UriMethodRelation> uriMethodRelateOptional = UriFactory.getInstance().getUriMethodRelate(uri);
        if (!uriMethodRelateOptional.isPresent()) {
            Log.warn("No handler found for URI: {}", uri);
            response.sendError(404, "该资源未找到");
            return;
        }

        // 执行处理并返回结果
        Optional<String> resultOptional = HandlerMapping.execute(uriMethodRelateOptional.get(), request.getParameterMap());
        if (resultOptional.isPresent()) {
            String result = resultOptional.get();
            response.setBody(result);
            response.send();
//            try (ServletOutputStream outputStream = response.getOutputStream()) {
//                outputStream.write(result.getBytes(StandardCharsets.UTF_8));
//                outputStream.flush();
//            }
        } else {
            Log.warn("Handler executed but returned no content for URI: {}", uri);
            response.sendError(404, "没有内容");
        }
    }
}

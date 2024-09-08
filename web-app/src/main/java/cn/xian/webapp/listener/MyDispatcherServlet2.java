package cn.xian.webapp.listener;

import cn.xian.springframework.HandlerMapping;
import cn.xian.springframework.beans.factory.UriFactory;
import cn.xian.springframework.beans.factory.config.UriMethodRelation;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static cn.xian.springframework.web.constant.WebConstant.TEXT_HTML_UTF_8;

/**
 * 前端控制器正常情况下放在spring-framework项目下，为了实现简易版本的tomcat（简易版本不引入servlet-api），当前的前端控制器先临时放在此处
 */
@Slf4j
public class MyDispatcherServlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDispatch(request, response);
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType(TEXT_HTML_UTF_8);
//        response.setContentType(APPLICATION_JSON_UTF_8);

        String uri = request.getRequestURI();
        log.debug("Received request for URI: {}", uri);

        // 根据 URI 查找对应的方法执行
        Optional<UriMethodRelation> uriMethodRelateOptional = UriFactory.getInstance().getUriMethodRelate(uri);
        if (!uriMethodRelateOptional.isPresent()) {
            log.warn("No handler found for URI: {}", uri);
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "该资源未找到");
            return;
        }

        // 执行处理并返回结果
        Optional<String> resultOptional = HandlerMapping.execute(uriMethodRelateOptional.get(), request.getParameterMap());
        if (resultOptional.isPresent()) {
            String result = resultOptional.get();
            try (ServletOutputStream outputStream = response.getOutputStream()) {
                outputStream.write(result.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } else {
            log.warn("Handler executed but returned no content for URI: {}", uri);
            response.sendError(HttpServletResponse.SC_NO_CONTENT, "没有内容");
        }
    }
}

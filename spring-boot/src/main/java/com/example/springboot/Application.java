package com.example.springboot;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class Application {

    public static void main(String[] args) {
        final ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        final WebServer webServer = serverFactory.getWebServer(servletContext -> {
            final HelloController helloController = new HelloController();

            servletContext.addServlet("frontcontroller", new HttpServlet() {
                @Override
                protected void service(final HttpServletRequest req, final HttpServletResponse resp) throws
                    IOException {
                    // 인증, 보안, 다국어 등 공통 기능 처리
                    if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                        // 어떤 로직을 수행할 것인가 매핑
                        final String name = req.getParameter("name");

                        final String ret = helloController.hello(name);  // 바인딩 -> 웹 기술 필요 XX, 분리

                        resp.setStatus(HttpStatus.OK.value());  // 상태 코드
                        resp.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE); // 헤더
                        resp.getWriter().println(ret);  // 바디
                    } else if (req.getRequestURI().equals("/user")) {
                        // 처리
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    } else {
                        resp.setStatus(HttpStatus.NOT_FOUND.value());
                    }
                }
            }).addMapping("/*");
        });
        webServer.start();
    }

}

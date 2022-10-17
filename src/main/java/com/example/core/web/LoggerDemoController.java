package com.example.core.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.core.common.MyLogger;

import lombok.RequiredArgsConstructor;

/**
 * request 스코프 빈은 요청이 와야 생성되는 데
 * 의존 주입을 하니깐 오류가 발생한다.
 */
@Controller
@RequiredArgsConstructor
public class LoggerDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}

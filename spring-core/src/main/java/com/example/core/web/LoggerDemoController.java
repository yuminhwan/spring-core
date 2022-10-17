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
 * 1.Provider 사용
 * 2. Proxy 사용
 */
@Controller
@RequiredArgsConstructor
public class LoggerDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        String requestURL = request.getRequestURL().toString();

        System.out.println(myLogger.getClass());
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1000);
        logDemoService.logic("testId");
        return "OK";
    }
}

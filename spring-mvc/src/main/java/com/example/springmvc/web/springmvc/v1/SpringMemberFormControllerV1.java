package com.example.springmvc.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *  RequestMappingHandlerMapping은 스프링 빈 중 @RequestMapping 또는 @Controller가
 *  클래스 레벨에 붙어 있는 경우에 매핑 정보로 인식한다.
 *  RequestMappingHandlerMapping의 isHandler라는 메서드를 보면
 *  어노테이션이 붙어 있는 지 확인한다.
 */
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}

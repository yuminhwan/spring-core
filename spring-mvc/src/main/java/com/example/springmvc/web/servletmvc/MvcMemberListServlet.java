package com.example.springmvc.web.servletmvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.springmvc.domain.member.Member;
import com.example.springmvc.domain.member.MemberRepository;

/**
 * MVC 컨트롤러의 단점
 * 1. 포워드 중복
 * 2. ViewPath에 중복
 *  2-1. jsp -> thymeleaf로 변경시 모두 변경해야함.
 * 3. 사용하지 않는 코드
 *   3-1. response는 사용하지 않음. -> 테스트 힘듬
 * 4. 공통 처리가 어렵다.
 *  -> 메서드로 추출하면?
 *  ->  해당 메서드를 항상 호출해야 하고, 실수로 호출하지 않으면 문제가 될 것이다. 그리고 호출하는 것 자체도 중복
 *
 * 결론 : 공통 처리가 어렵다는 문제
 */
@WebServlet(name = "mvcMemberListServlet", urlPatterns = "/servlet-mvc/members")
public class MvcMemberListServlet extends HttpServlet {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws
        ServletException, IOException {
        List<Member> members = memberRepository.findAll();

        request.setAttribute("members", members);

        String viewPath = "/WEB-INF/views/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}

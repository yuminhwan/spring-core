package com.example.springmvc.web.frontcontroller.v2.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.springmvc.domain.member.Member;
import com.example.springmvc.domain.member.MemberRepository;
import com.example.springmvc.web.frontcontroller.MyView;
import com.example.springmvc.web.frontcontroller.v2.ControllerV2;

/**
 * MyView를 통해 중복 로직 제거
 * 하지만 request, response가 필요없는 쪽에서도 파라미터로 받고 있음
 */
public class MemberListControllerV2 implements ControllerV2 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws
        ServletException,
        IOException {
        List<Member> members = memberRepository.findAll();
        request.setAttribute("members", members);
        return new MyView("/WEB-INF/views/members.jsp");
    }
}

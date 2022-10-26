package com.example.springmvc.web.frontcontroller.v3.controller;

import java.util.List;
import java.util.Map;

import com.example.springmvc.domain.member.Member;
import com.example.springmvc.domain.member.MemberRepository;
import com.example.springmvc.web.frontcontroller.ModelView;
import com.example.springmvc.web.frontcontroller.v3.ControllerV3;

/**
 *  Model 추가로 인해
 *  1. 서블릿 종속성 제거
 *    -> 서블릿 기술을 모름으로써 단순해지고, 테스트도 쉽다.
 *  2. 뷰 이름 중복 제거
 *    -> .jsp, 폴더 위치를 반환하는 것이 아닌 뷰의 논리 이름만 반환
 *    -> 위치가 변경되도 앞단에서 바꿔주면 된다.
 *
 *  하지만 항상 ModelView 객체를 생성하고 반환해야 하는 번거로움이 있다.
 */
public class MemberListControllerV3 implements ControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {
        List<Member> members = memberRepository.findAll();
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);
        return mv;
    }
}

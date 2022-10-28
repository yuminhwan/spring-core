package com.example.springmvc.web.frontcontroller.v4.controller;

import java.util.List;
import java.util.Map;

import com.example.springmvc.domain.member.Member;
import com.example.springmvc.domain.member.MemberRepository;
import com.example.springmvc.web.frontcontroller.v4.ControllerV4;

/**
 * model을 파라미터로 받고 뷰의 논리 이름을 반환함으로써 좀 더 코드가 간단해짐.
 *
 * 하지만 Controller의 모양이 고정되어 있다. -> 파라미터를 고정되게 받아야한다.
 * -> 언제는 ControllerV3, 다른 때는ControllerV4를 쓰고싶다면??
 */
public class MemberListControllerV4 implements ControllerV4 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();

        model.put("members", members);
        return "members";
    }
}

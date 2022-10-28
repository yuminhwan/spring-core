package com.example.springmvc.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    private static final MemberRepository instnace = new MemberRepository();
    private static long sequence = 0L;
    private final Map<Long, Member> store = new HashMap<>();

    private MemberRepository() {
    }

    public static MemberRepository getInstance() {
        return instnace;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}

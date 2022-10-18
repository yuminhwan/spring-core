package com.example.springdb.service;

import java.sql.SQLException;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.springdb.domain.Member;
import com.example.springdb.repository.MemberRepositoryV3;

import lombok.extern.slf4j.Slf4j;

/**
 * 트랜잭션 - 트랜잭션 템플릿
 */
@Slf4j
public class MemberServiceV3_2 {

    private final TransactionTemplate transactionTemplate;
    private final MemberRepositoryV3 memberRepository;

    /**
     * PlatformTransactionManger를 주입받는 이유는 관례인 것도 있고
     * TransactionTemplate 자체는 그저 클래스이기 때문에 유연성이 없다.
     * -> PlatformTransactionManger는 자유롭게 변경하여 사용가능
     */
    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        transactionTemplate.executeWithoutResult((status) -> {
                // 비즈니스 로직
                try {
                    bizLogic(fromId, toId, money);
                } catch (SQLException e) {
                    throw new IllegalStateException(e);
                }
            }
        );
    }

    private void bizLogic(String fromId, String toId, int money) throws SQLException {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}

package com.example.springjpa.hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * GeneratedValue - 자동 키 매핑
 * 1. IDENTITY
 *   - 기본 키 생성을 데이터베이스에 위임
 *   - 주로 MySQL, PostgreSQL 등
 *   - JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL이 실행되지만 AUTO_INCREMENT는 데이터베이스에
 *     INSERT SQL을 실행한 이후에 ID값을 알 수 있음. 그렇기 때문에 em.persist() 시점에 즉시
 *     INSERT SQL실행
 *
 * 2. SEQUENCE
 *   - 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트
 *   - 오라클, PostgreSQL, H2 등
 *   - allocationSize을 통한 성능 최적화 가능
 *   - DB SEQ = 1  |  1
 *   - DB SEQ = 51 |  2
 *   - DB SEQ = 51 |  3
 *   - 미리 해당하는 ID값의 size을 할당받는 방식
 *      - 여러 WAS여도 문제 없이 동작 가능
 *
 * 3. TABLE
 *   - 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
 *   - 장점 : 모든 데이터베이스 적용 가능
 *   - 단점 : 성능
 */
@Entity
@SequenceGenerator(
    name = "MEMBER_SEQ_GENERATOR",
    sequenceName = "MEMBER_SEQ",
    initialValue = 1, allocationSize = 50)
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    protected MemberEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

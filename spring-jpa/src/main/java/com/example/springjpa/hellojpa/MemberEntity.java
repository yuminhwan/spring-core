package com.example.springjpa.hellojpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
public class MemberEntity {

    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) // 기본 : ORDINAL -> 순서 저장 -> 순서 변경 시 영향 -> 위험!!
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP) // LocalDate, LocalDateTime을 사용할 때는 생략 가능(최신 하이버네이트 지원)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    @Transient
    private int temp;

    protected MemberEntity() {
    }
}

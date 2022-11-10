package com.example.springjpa.inheritance;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

/**
 *
 * @Entity -> extends 상속관계
 *
 * @MappedSuperclass extends
 * 상속관계 매핑X
 * 엔티티X, 테이블과 매핑X -> 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
 * 조회, 검색 불가 em.find(BaseEntity) X
 * 직접 생성해서 사용할 일이 없으므로 추상 클래스 권장
 *
 * 테이블과 관계 없고, 단순히 엔티티가 공통으로
 * 사용하는 매핑 정보를 모으는 역할
 */
@MappedSuperclass
public abstract class BaseEntity {

    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}

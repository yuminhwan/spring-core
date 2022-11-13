package com.example.springjpashop.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    /**
     * 객체지향을 생각하면 ID보단 Member 객체를 가지고 있는 것이 맞지 않을까?
     * 현재는 객체 설계를 테이블 설계에 맞춘 방식
     *   - 테이블의 외래키를 객체에 그대로 가져옴
     *   - 객체 그래프 탐색이 불가능
     *   - 참조가 없으므로 UML도 잘못됨
     */
    @Column(name = "MEMBER_ID")
    private Long memberId;

    private LocalDateTime orderDate;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}

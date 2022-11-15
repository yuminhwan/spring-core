package com.example.springjpashop.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Delivery {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;
    
    private String city;
    private String street;
    private String zipcode;

    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus status;

}

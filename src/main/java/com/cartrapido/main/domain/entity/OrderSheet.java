package com.cartrapido.main.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orderSheet")
public class OrderSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long orderNum;

    @Column(length = 100, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private Long productId;

    @Column(length = 100, nullable = false)
    private int amount;




    @Builder
    public OrderSheet(Long orderNum, String userEmail, Long productId, int amount ) {
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.productId = productId;
        this.amount = amount;
    }



}

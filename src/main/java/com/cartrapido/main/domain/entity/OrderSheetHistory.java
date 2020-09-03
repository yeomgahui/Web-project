package com.cartrapido.main.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "orderSheetHistory")
public class OrderSheetHistory {

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

    @Column
    private String store;

    @Column
    private String productName;

    @Column
    private int productPrice;

    @Column
    private String image;


    @Builder
    public OrderSheetHistory(Long orderNum, String userEmail, Long productId, int amount,
                             String store, String productName, int productPrice, String image
                        ) {
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.productId = productId;
        this.amount = amount;
        this.store = store;
        this.productName = productName;
        this.productPrice = productPrice;
        this.image = image;
    }



}

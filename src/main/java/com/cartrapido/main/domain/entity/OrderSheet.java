package com.cartrapido.main.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orderSheet")
public class OrderSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long orderNum;

    @Column(nullable = false)
    private Long productId;

    @Column(length = 100, nullable = false)
    private int amount;

    @Column
    private String store;

    @Builder
    public OrderSheet(Long orderNum, Long productId, int amount, String store
                        ) {
        this.orderNum = orderNum;
        this.productId = productId;
        this.amount = amount;
        this.store = store;
    }



}

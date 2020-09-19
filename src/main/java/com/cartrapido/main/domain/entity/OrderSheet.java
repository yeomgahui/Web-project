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

    @Column
    private String storeName;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Column(columnDefinition = "double default 0")
    private double distance;



    @Builder
    public OrderSheet(Long orderId, Long orderNum, Long productId, int amount, String store, String storeName,
                      double latitude, double longitude, double distance ) {
        this.orderId = orderId;
        this.orderNum = orderNum;
        this.productId = productId;
        this.amount = amount;
        this.store = store;
        this.storeName= storeName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }



}

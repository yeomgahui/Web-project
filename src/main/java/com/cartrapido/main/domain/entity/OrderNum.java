package com.cartrapido.main.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sun.rmi.runtime.Log;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orderNum")
public class OrderNum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNum;

    @Column(length = 200)
    private String userEmail;

    @Column(length = 200)
    private String shopper;

    @Column(length = 200)
    private int latitud;

    @Column(length = 200)
    private int longitude;

    @Builder
    public OrderNum(String userEmail, String shopper , int latitud, int longitude){
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.latitud = latitud;
        this.longitude = longitude;
    }

    @Builder
    public OrderNum(Long orderNum, String userEmail, String shopper , int latitud, int longitude){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.latitud = latitud;
        this.longitude = longitude;
    }



}




























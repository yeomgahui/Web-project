package com.cartrapido.main.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orderNumHistory")
public class OrderNumHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNum;

    @Column
    private Long oriOrderNum;

    @Column
    private String userEmail;

    @Column
    private String shopper;

    @Column
    private int deliveryCost;

    @Column
    private int productTot;

    @Column
    private String pay;//1 = true  , 0 = false

    @Column
    private String address;

    @Column
    private String detailAddress;

    @Column
    private String request;

    @Column
    private String agree;

    @Column(updatable = false)
    private LocalDate createdDate;


    @Builder
    public OrderNumHistory(Long oriOrderNum,String userEmail, String shopper ,
                           int deliveryCost, int productTot , String pay, String address, String detailAddress, String agree,
                            LocalDate createdDate, String request){
        this.oriOrderNum = oriOrderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
        this.address = address;
        this.detailAddress = detailAddress;
        this.agree = agree;
        this.createdDate = createdDate;
        this.request = request;
    }

    @Builder
    public OrderNumHistory(Long orderNum, String userEmail, String shopper ){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
    }




}




























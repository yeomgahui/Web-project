package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.OrderNum;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
@Getter
public class OrderNumDTO {
    private Long orderNum;
    private String userEmail;
    private String shopper;
    private int deliveryCost;
    private int productTot;
    private String pay;
    private String address;
    private String detailAddress;
    private String request;
    private String agree;



    public OrderNum toEntitiy(){
        OrderNum orderNum = OrderNum.builder()
                .userEmail(userEmail)
                .shopper(shopper)
                .deliveryCost(deliveryCost)
                .productTot(productTot)
                .pay(pay)
                .address(address)
                .detailAddress(detailAddress)
                .build();
        return orderNum;
    }

    public OrderNumDTO(Long orderNum, String userEmail, String shopper,
                       int deliveryCost, int productTot, String pay){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
    }

    public OrderNumDTO(String userEmail, String shopper,
                       int deliveryCost, int productTot, String pay
                    ){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
    }

    public OrderNumDTO(Long orderNum, String userEmail, String shopper,
                       int deliveryCost, int productTot, String pay,
                       String address , String detailAddress,
                       String agree, String request){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
        this.agree =agree;
        this.request =request;
        this.address = address;
        this.detailAddress = detailAddress;
    }

    @Builder
    public OrderNumDTO(String userEmail, String shopper,
                       int deliveryCost, int productTot,
                       String address, String detailAddress){
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
    }

    @Builder
    public OrderNumDTO(Long orderNum, String userEmail, String shopper){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
    }




}

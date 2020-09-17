package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import java.time.LocalDate;

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
    private LocalDate createdDate;
    //위도 경도
    private int latitude;
    private int longitude;

    @Builder
    public OrderNumDTO(Long orderNum, String userEmail, String shopper, int deliveryCost,
                       int productTot, String pay, String address, String detailAddress,
                       String agree, String request, LocalDate createdDate,
                       int latitude, int longitude) {
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
        this.createdDate = createdDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public OrderNum toEntitiy(){
        OrderNum orderNum = OrderNum.builder()
                .userEmail(userEmail)
                .shopper(shopper)
                .deliveryCost(deliveryCost)
                .productTot(productTot)
                .pay(pay)
                .address(address)
                .detailAddress(detailAddress)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        return orderNum;
    }



    public OrderNumDTO(Long orderNum, String userEmail, String shopper,
                       int deliveryCost, int productTot, String pay, LocalDate createdDate){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
        this.createdDate = createdDate;
    }





}

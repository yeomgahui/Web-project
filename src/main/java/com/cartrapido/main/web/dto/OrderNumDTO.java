package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;

@Setter
@Getter
public class OrderNumDTO {
    private Long orderNum;
    private String userEmail;
    private String shopper;
    private int deliveryCost;
    private int productTot;
    private int pay;
    private String address;
    private String detailAddress;
    private String request;
    private String agree;
    private LocalDate createdDate;
    //위도 경도
    private double latitude;
    private double longitude;
    //거리
    private double distance;

    @Builder
    public OrderNumDTO(Long orderNum, String userEmail, String shopper, int deliveryCost,
                       int productTot, int pay, String address, String detailAddress,
                       String agree, String request, LocalDate createdDate,
                       double latitude, double longitude, double distance) {
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
        this.distance = distance;
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
                       int deliveryCost, int productTot, int pay, LocalDate createdDate, double distance){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
        this.createdDate = createdDate;
        this.distance = distance;
    }

    public OrderNumDTO(Long orderNum, String userEmail, String shopper,
                       int deliveryCost, int productTot, int pay, LocalDate createdDate,double latitude, double longitude ,double distance){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
        this.createdDate = createdDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }





}

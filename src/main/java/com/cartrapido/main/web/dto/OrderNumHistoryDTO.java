package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OrderNumHistoryDTO {
    private Long orderNum;
    private Long oriOrderNum;
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

    @Builder
    public OrderNumHistoryDTO(Long orderNum, Long oriOrderNum,
                              String userEmail, String shopper,
                              int deliveryCost, int productTot,
                              String pay, String address, String detailAddress,
                              String agree, String request, LocalDate createdDate) {
        this.orderNum = orderNum;
        this.oriOrderNum = oriOrderNum;
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
    }

    public OrderNumHistory toEntitiy(){
        OrderNumHistory orderNumHistory = OrderNumHistory.builder()
                .oriOrderNum(oriOrderNum)
                .userEmail(userEmail)
                .shopper(shopper)
                .deliveryCost(deliveryCost)
                .productTot(productTot)
                .pay(pay)
                .address(address)
                .detailAddress(detailAddress)
                .request(request)
                .createdDate(createdDate)
                .agree(agree)
                .build();
        return orderNumHistory;
    }



    public OrderNumHistoryDTO(Long orderNum, String userEmail, String shopper,
                              int deliveryCost, int productTot, String pay){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
    }


    public OrderNumHistoryDTO(Long orderNum, String userEmail, String shopper,
                              int deliveryCost, int productTot, String pay,
                              String address , String detailAddress,
                              String agree, String request, LocalDate createdDate){
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
    }



}

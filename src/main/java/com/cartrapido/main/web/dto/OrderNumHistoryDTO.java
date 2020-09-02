package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import lombok.Builder;
import lombok.Getter;

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

    public OrderNumHistoryDTO(String userEmail, String shopper,
                              int deliveryCost, int productTot, String pay
                    ){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
        this.pay = pay;
    }

    public OrderNumHistoryDTO(Long orderNum,Long oriOrderNum, String userEmail, String shopper,
                              int deliveryCost, int productTot, String pay,
                              String address , String detailAddress,
                              String agree, String request){
        this.oriOrderNum = oriOrderNum;
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

    public OrderNumHistoryDTO(Long orderNum, String userEmail, String shopper,
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
    public OrderNumHistoryDTO(String userEmail, String shopper,
                              int deliveryCost, int productTot,
                              String address, String detailAddress){
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
    }

    @Builder
    public OrderNumHistoryDTO(Long orderNum, String userEmail, String shopper){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
    }




}

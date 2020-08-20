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
    private int latitud;
    private int longitude;
    private int deliveryCost;
    private int productTot;


    public OrderNum toEntitiy(){
        OrderNum orderNum = OrderNum.builder()
                .userEmail(userEmail)
                .shopper(shopper)
                .latitud(latitud)
                .longitude(longitude)
                .deliveryCost(deliveryCost)
                .productTot(productTot)
                .build();
        return orderNum;
    }

    @Builder
    public OrderNumDTO(String userEmail, String shopper, int latitud, int longitude,  int deliveryCost, int productTot){
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.latitud = latitud;
        this.longitude = longitude;
        this.deliveryCost = deliveryCost;
        this.productTot = productTot;
    }

    @Builder
    public OrderNumDTO(Long orderNum, String userEmail, String shopper, int latitud, int longitude){
        this.orderNum = orderNum;
        this.userEmail = userEmail;
        this.shopper = shopper ;
        this.latitud = latitud;
        this.longitude = longitude;
    }




}

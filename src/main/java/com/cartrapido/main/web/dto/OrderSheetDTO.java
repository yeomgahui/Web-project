package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.OrderSheet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class OrderSheetDTO {

    private Long orderId;
    private Long orderNum;
    private Long productId;
    private int amount;
    private String store;

    //위도 경도
    private double latitude;
    private double longitude;

    //기본 외 받아야할 product 정보를
    private String productName;
    private int productPrice;
    private String image;

    //storeRank처리하기 위한 생성자
    public OrderSheetDTO(){}

    public OrderSheet toEntitiy(){
        OrderSheet orderSheet = OrderSheet.builder()
                .orderNum(orderNum)
                .productId(productId)
                .amount(amount)
                .store(store)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        return orderSheet;

    }

    @Builder //테이블에 값을 넣을때 쓰는 빌더
    public OrderSheetDTO(Long orderNum, String userEmail, Long productId, int amount,
                        String productName,int productPrice,String store,String image,
                         double latitude, double longitude){
        this.orderNum=orderNum;
        this.productId = productId;
        this.amount = amount;
        this.productName=productName;
        this.productPrice=productPrice;
        this.store=store;
        this.image=image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //테이블 외 값으로 shopper에게 보여줄 값들.
    public void setOtherInfo(String productName, int productPrice, String store, String image){
        this.productName = productName;
        this.productPrice = productPrice;
        this.store = store;
        this.image = image;
    }

    //위도 경도 set하기
    public void setLocation(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
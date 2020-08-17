package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.OrderSheet;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSheetDTO {

    private Long orderId;
    private Long orderNum;
    private String userEmail;
    private Long productId;
    private int amount;

    //기본 외 받아야할 product 정보를
    private String productName;
    private int productPrice;
    private String store;
    private String image;

    public OrderSheet toEntitiy(){
        OrderSheet orderSheet = OrderSheet.builder()
                .orderNum(orderNum)
                .userEmail(userEmail)
                .productId(productId)
                .amount(amount)
                .build();
        return orderSheet;

    }

    @Builder //테이블에 값을 넣을때 쓰는 빌더
    public OrderSheetDTO(Long orderNum, String userEmail, Long productId, int amount){
        this.orderNum=orderNum;
        this.userEmail =userEmail;
        this.productId = productId;
        this.amount = amount;
    }

    @Builder //테이블 외 값으로 shopper에게 보여줄 값들.
    public void setOtherInfo(String productName, int productPrice, String store, String image){
        this.productName = productName;
        this.productPrice = productPrice;
        this.store = store;
        this.image = image;

    }

}

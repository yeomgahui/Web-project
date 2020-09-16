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
    private Long productId;
    private int amount;
    private String store;

    //기본 외 받아야할 product 정보를
    private String productName;
    private int productPrice;
    private String image;

    public OrderSheetDTO() {

    }

    public OrderSheet toEntitiy(){
        OrderSheet orderSheet = OrderSheet.builder()
                .orderNum(orderNum)
                .productId(productId)
                .amount(amount)
                .store(store)
                .build();
        return orderSheet;

    }

    @Builder //테이블에 값을 넣을때 쓰는 빌더
    public OrderSheetDTO(Long orderNum, String userEmail, Long productId, int amount,
                        String productName,int productPrice,String store,String image
                    ){
        this.orderNum=orderNum;
        this.productId = productId;
        this.amount = amount;
        this.productName=productName;
        this.productPrice=productPrice;
        this.store=store;
        this.image=image;
    }

    //테이블 외 값으로 shopper에게 보여줄 값들.
    public void setOtherInfo(String productName, int productPrice, String store, String image){
        this.productName = productName;
        this.productPrice = productPrice;
        this.store = store;
        this.image = image;
    }

}

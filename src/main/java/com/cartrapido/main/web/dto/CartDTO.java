package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Cart;
import lombok.Builder;
import lombok.Getter;


@Getter
public class CartDTO {
    private Long cartId;
    private String userEmail;
    private Long productId;
    private int amount;

    //기본 외 받아야할 product 정보를
    private String productName;
    private int productPrice;
    private String image;
    private String store;

    public Cart toEntity(){
        Cart cart = Cart.builder()
                .cartId(cartId)
                .userEmail(userEmail)
                .productId(productId)
                .amount(amount)
                .build();
        return cart;
    }

    @Builder
    public CartDTO(Long cartId , String userEmail , Long productId, int amount){
        this.cartId  = cartId;
        this.userEmail = userEmail;
        this.productId = productId;
        this.amount = amount;
    }

    //테이블 외 값으로 shopper에게 보여줄 값들.
    public void setOtherInfo(String productName, int productPrice, String store, String image){
        this.productName = productName;
        this.productPrice = productPrice;
        this.store = store;
        this.image = image;
    }
}

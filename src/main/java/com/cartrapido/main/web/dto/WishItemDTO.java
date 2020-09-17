package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.entity.WishItem;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class WishItemDTO {

    private Long wiSequence;
    private Long productId;
    private String email;


    //기본 외 받아야할 product 정보를
    private String productName;
    private int productPrice;
    private String image;
    private String productContent;

    public WishItem toEntity(){
        WishItem wishItem = WishItem.builder()
                .wiSequence(wiSequence)
                .productId(productId)
                .email(email)
                .build();
        return wishItem;
    }

    @Builder
    public WishItemDTO(Long wiSequence,
                       Long productId,
                       String email){
        this.wiSequence = wiSequence;
        this.productId = productId;
        this.email = email;
    }

    //테이블 외 값으로 shopper에게 보여줄 값들.
    public void setOtherInfo(String productName, int productPrice, String productContent, String image){
        this.productName = productName;
        this.productPrice = productPrice;
        this.productContent = productContent;
        this.image = image;
    }


}

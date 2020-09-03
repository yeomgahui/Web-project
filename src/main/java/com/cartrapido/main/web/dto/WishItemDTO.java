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
    private String itemId;
    private String productName;
    private int productPrice;
    private String productContent;
    private String store;
    private String category;
    private String image;
    private String email;

    //필요한지? 고민
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public WishItem toEntity(){
        WishItem wishItem = WishItem.builder()
                .wiSequence(wiSequence)
                .productId(productId)
                .itemId(itemId)
                .productName(productName)
                .productPrice(productPrice)
                .productContent(productContent)
                .store(store)
                .category(category)
                .image(image)
                .email(email)
                .build();
        return wishItem;
    }

    @Builder
    public WishItemDTO(Long wiSequence,
                       Long productId,
                       String itemId,
                       String productName,
                       String productContent,
                       int productPrice,
                       String store,
                       String category,
                       String image,
                       String email){
        this.wiSequence = wiSequence;
        this.productId = productId;
        this.itemId = itemId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productContent = productContent;
        this.store = store;
        this.category = category;
        this.image = image;
        this.email = email;
    }

}

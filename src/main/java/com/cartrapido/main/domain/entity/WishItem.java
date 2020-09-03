package com.cartrapido.main.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "wishItem")
public class WishItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wiSequence;


    @Column
    private Long productId;

    @Column
    private String itemId;

    @Column
    private String store;

    @Column
    private String category;

    @Column
    private String productName;

    @Column
    private int productPrice;

    @Column(columnDefinition = "TEXT")
    private String productContent;

    @Column
    private String image;

    @Column(nullable = false)
    private String email;

    @Builder
    public WishItem(Long wiSequence,
                    Long productId,
                   String itemId,
                   String productName,
                   String productContent,
                   int productPrice,
                   String store,
                   String category,
                   String image,
                    String email) {

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

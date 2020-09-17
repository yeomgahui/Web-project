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

    @Column(nullable = false)
    private String email;

    @Builder
    public WishItem(Long wiSequence,
                    Long productId,
                    String email) {
        this.wiSequence = wiSequence;
        this.productId = productId;
        this.email = email;
    }

}

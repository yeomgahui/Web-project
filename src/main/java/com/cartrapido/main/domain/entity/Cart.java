package com.cartrapido.main.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.lang.ref.Reference;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(length = 100, nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int amount;

    @Builder
    public Cart(Long cartId, String userEmail, Long productId, int amount) {
        this.cartId = cartId;
        this.userEmail = userEmail;
        this.productId = productId;
        this.amount = amount;
    }


}

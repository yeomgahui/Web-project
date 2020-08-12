package com.cartrapido.main.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
<<<<<<< HEAD
    
    SHOPPER("ROLE_SHOPPER","배달자"),
    USER("ROLE_USER","일반 사용자"),
    ADMIN("ROLE_ADMIN","관리자");


=======
    USER("ROLE_USER","일반 사용자"),
    ADMIN("ROLE_ADMIN","관리자");

>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
    private final String key;
    private final String title;
}

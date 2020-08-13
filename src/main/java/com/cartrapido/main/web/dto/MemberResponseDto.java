package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String email;

    public MemberResponseDto(Member entity){
        this.id = entity.getId();
        this.email = entity.getEmail();
    }
}

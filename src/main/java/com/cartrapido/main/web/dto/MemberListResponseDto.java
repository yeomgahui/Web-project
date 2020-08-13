package com.cartrapido.main.web.dto;


import com.cartrapido.main.domain.entity.Member;
import lombok.Getter;

@Getter
public class MemberListResponseDto {
    private Long id;
    private String email;
    private String address;

    public MemberListResponseDto(Member entity){
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.address = entity.getAddress();
    }
}

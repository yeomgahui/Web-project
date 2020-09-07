package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Member;
import lombok.Getter;

@Getter
public class MemberListDTO {
    private String email;
    private String name;
    private String address;
    private String role;
    private Boolean enable;

    public MemberListDTO(Member entity){
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.role = entity.getRoleKey();
        this.enable = entity.getEnable();
    }
}

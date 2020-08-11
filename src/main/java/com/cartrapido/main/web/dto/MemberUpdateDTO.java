package com.cartrapido.main.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateDTO {

    private String password;

    @Builder
    public MemberUpdateDTO(String password){
        this.password = password;
    }
}

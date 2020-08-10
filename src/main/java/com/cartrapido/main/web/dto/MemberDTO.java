package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String email;
    private String password;
    private String address;

    public Member toEntity(){
        return Member.builder()
                .email(email)
                .password(password)
                .address(address)
                .role(Role.USER)
                .build();
    }

    @Builder
    public MemberDTO(Long id, String email, String password,String address) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.password = password;
    }
}

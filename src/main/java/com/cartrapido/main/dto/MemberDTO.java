package com.cartrapido.main.dto;

import com.cartrapido.main.domain.user.Member;
import com.cartrapido.main.domain.user.Role;
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
                .id(id)
                .address(address)
                .email(email)
                .password(password)
                .role(Role.USER)
                .build();
    }

    public MemberDTO(Long id, String email,String address, String password){
        this.id = id;
        this.email = email;
        this.address = address;
        this.password = password;
    }
}

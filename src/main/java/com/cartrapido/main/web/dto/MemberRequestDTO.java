package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.Member;
import com.cartrapido.main.domain.Role;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class MemberRequestDTO {

    private Long id;

    @NotBlank(message = "이름을 작성해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private String address;
    private String role;

    @NotBlank(message = "메일을 작성해주세요.")
    @Email(message = "메일의 양식을 지켜주세요.")
    private String email;

    public MemberRequestDTO() {}

    public Member toEntity(){
        Role role = null;
        if(this.role.equals("USER")){
            role = Role.USER;
        }else{
            role = Role.SHOPPER;
        }

        return Member.builder()
                .email(email)
                .password(password)
                .address(address)
                .name(name)
                .role(role)
                .build();
    }

    @Builder
    public MemberRequestDTO(Long id, String role, String name, String email, String password,String address) {
        this.id = id;
        this.email = email;
        this.address = address;
        this.name = name;
        this.role = role;
        this.password = password;
    }
}

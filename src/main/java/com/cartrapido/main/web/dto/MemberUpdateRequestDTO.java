package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberUpdateRequestDTO {
    private Long id;

    @NotBlank(message = "이름을 작성해주세요.")
    private String name;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "주소를 입력해 주세요.")
    private String address;



    public Member toEntity(){

        return Member.builder()
                .password(password)
                .address(address)
                .name(name)
                .build();
    }

    @Builder
    public MemberUpdateRequestDTO(String name, String password, String address) {
        this.address = address;
        this.name = name;
        this.password = password;
    }
}

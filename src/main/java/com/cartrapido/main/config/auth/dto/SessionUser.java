package com.cartrapido.main.config.auth.dto;

import com.cartrapido.main.domain.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String address;
    private int point;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.address = member.getAddress();
        this.point = member.getPoint();
    }
}

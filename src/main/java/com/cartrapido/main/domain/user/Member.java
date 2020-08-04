package com.cartrapido.main.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Member(Long id, String name, String email, String password, String address, Role role){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }


    public Member update(String name,String address){
        System.out.println("주소 "+address);
        this.name = name;
        if(!address.equals("null")){
            this.address = address;
        }

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

}

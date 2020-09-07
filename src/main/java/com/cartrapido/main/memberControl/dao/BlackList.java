package com.cartrapido.main.memberControl.dao;

import com.cartrapido.main.domain.entity.TimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BlackList extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //신고 당하는 사람
    @Column(nullable = false)
    private String useremail;

    //신고 하는 사람
    @Column(nullable = false)
    private String shopper;

    @Column(nullable = false)
    private String content;


    @Builder
    public BlackList(String useremail, String shopper, String content){
        this.useremail = useremail;
        this.shopper = shopper;
        this.content = content;
    }

}
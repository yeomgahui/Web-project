package com.cartrapido.main.domain.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "QnA")
public class QnA extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ref; //그룹번호

    @Column
    private int lev; //단계

    @Builder
    public QnA(int seq,
               String name,
               String email,
               String title,
               String content,
               int ref,
               int lev) {

        this.seq = seq;
        this.name = name;
        this.email = email;
        this.title = title;
        this.content = content;
        this.ref = ref;
        this.lev = lev;
    }


}

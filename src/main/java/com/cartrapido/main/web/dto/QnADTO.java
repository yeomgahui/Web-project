package com.cartrapido.main.web.dto;

import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.entity.QnA;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QnADTO {

    private int seq;
    private String name;
    private String email;
    private String title;
    private String content;

    private int ref; //그룹 번호
    private int lev; //단계

    private LocalDate createdDate;

    public QnADTO(QnA qnA) {
        this.seq = qnA.getSeq();
        this.name = qnA.getName();
        this.email = qnA.getEmail();
        this.title = qnA.getTitle();
        this.content = qnA.getContent();
        this.ref = qnA.getRef();
        this.lev = qnA.getLev();
        this.createdDate = qnA.getCreatedDate();
    }

    public QnA toEntity() {
        QnA qna = QnA.builder()
                .seq(seq)
                .name(name)
                .email(email)
                .title(title)
                .content(content)
                .ref(ref)
                .lev(lev)
                .build();
        return qna;
    }

    @Builder
    public QnADTO(int seq,
                  String name,
                  String email,
                  String title,
                  String content,
                  int ref,
                  int lev,
                  LocalDate createdDate) {

        this.seq = seq;
        this.name = name;
        this.email = email;
        this.title = title;
        this.content = content;
        this.ref = ref;
        this.lev = lev;
        this.createdDate = createdDate;
    }
}
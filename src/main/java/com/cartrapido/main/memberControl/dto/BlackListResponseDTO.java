package com.cartrapido.main.memberControl.dto;

import com.cartrapido.main.memberControl.dao.BlackList;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BlackListResponseDTO {

    private Long id;
    private String useremail;
    private String shopper;
    private LocalDate modifiedDate;
    private String content;

    public BlackListResponseDTO(BlackList entity){
        this.id = entity.getId();
        this.useremail = entity.getUseremail();
        this.shopper = entity.getShopper();
        this.modifiedDate = entity.getModifiedDate();
        this.content = entity.getContent();
    }
}

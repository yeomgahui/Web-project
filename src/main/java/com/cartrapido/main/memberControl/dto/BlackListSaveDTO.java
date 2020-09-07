package com.cartrapido.main.memberControl.dto;

import com.cartrapido.main.memberControl.dao.BlackList;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BlackListSaveDTO {
    private String useremail;
    private String shopper;
    private String content;

    @Builder
    public BlackListSaveDTO(String useremail, String shopper, String content){
        this.useremail = useremail;
        this.shopper = shopper;
        this.content = content;
    }

    public BlackList toEntity(){
        return BlackList.builder()
                .useremail(useremail)
                .shopper(shopper)
                .content(content)
                .build();
    }

}

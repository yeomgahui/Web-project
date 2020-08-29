package com.cartrapido.main.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public
class OrderExtraInfoDTO {
    private Long orderNum;
    private String address;
    private String detailAddress;
    private String agree;
    private String request;

}

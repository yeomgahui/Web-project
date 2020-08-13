package com.cartrapido.main.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailSendDTO {
    private String address;
    private String title;
    private String message;
}

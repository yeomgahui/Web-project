package com.cartrapido.main.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

//test01
@Service
@AllArgsConstructor
public class MailService {
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setProtocol("SMTP");
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);

        return javaMailSender;
    }


    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "ykh4933@gmail.com";




    public void mailSend(String email,String tempPwd) {
        System.out.println("mainSend 진입");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject("VM? 비밀번호 초기화");
        message.setText("임시 비밀번호는 :"+tempPwd+" 입니다.");

        try {
            mailSender.send(message);
        }catch (MailException e){
            e.printStackTrace();
        }
    }
}

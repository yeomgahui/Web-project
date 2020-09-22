package com.cartrapido.main.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

//test01
@Service
@AllArgsConstructor
@Configuration
public class MailService {

    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "ykh4933@gmail.com";

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSender() {
            @Override
            public void send(SimpleMailMessage simpleMessage) throws MailException {

            }

            @Override
            public void send(SimpleMailMessage... simpleMessages) throws MailException {

            }

            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage mimeMessage) throws MailException {

            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {

            }

            @Override
            public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

            }

            @Override
            public void send(MimeMessagePreparator... mimeMessagePreparators) throws MailException {

            }
        };
    }






    public void mailSend(String email,String tempPwd) {
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
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

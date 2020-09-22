package com.cartrapido.main;




import com.cartrapido.main.exception.ValidCustomException;
import org.omg.CORBA.ServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.request.WebRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@EnableJpaAuditing
@SpringBootApplication(exclude={BatchAutoConfiguration.class})
public class Application{

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {

            @Override
            public Map<String, Object> getErrorAttributes(
                    WebRequest webRequest,
                    boolean includeStackTrace) {

                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
                Throwable error = getError(webRequest);
                if (error instanceof ValidCustomException) {
                    errorAttributes.put("errors", ((ValidCustomException)error).getErrors());
                }
                return errorAttributes;
            }

        };
    }


    public static void main(String[] args) {
        //Test
        SpringApplication.run(Application.class, "--debug");
    }



}

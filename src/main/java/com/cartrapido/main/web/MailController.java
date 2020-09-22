package com.cartrapido.main.web;

import com.cartrapido.main.service.MailService;
import com.cartrapido.main.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@AllArgsConstructor
@Controller
public class MailController {


    private final MailService mailService;
    private final MemberService memberService;

    @GetMapping("/mail")
    public String dispMail() {
        return "mailSend.html";
    }

    @PostMapping("/mail")
    public @ResponseBody String execMail(@RequestParam(name="email") String email) {
        String tempPwd = memberService.verifyEmail(email);
        if(tempPwd != null){
            mailService.mailSend(email,tempPwd);
            return "입력하신 이메일로 임시 비밀번호를 보냈습니다. 이메일함을 확인해주세요.";
        }
        return "없는 아이디 입니다. 회원가입을 먼저 해주세요.";
    }

}

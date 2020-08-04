package com.cartrapido.main.web;

import com.cartrapido.main.dto.MemberDTO;
import com.cartrapido.main.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    //회원가입 처리
    /*@PostMapping("/user/signUpPage")
    public String execSignup(MemberDTO memberDTO){
        memberService.joinUser(memberDTO);
        return "redirect:/user/loginSuccess";
    }*/
    /*@PostMapping("/user/signUpPage")
    public Long execSignup(MemberDTO memberDTO){

        return memberService.joinUser(memberDTO);
    }*/

}

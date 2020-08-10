package com.cartrapido.main.web;

import com.cartrapido.main.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

  /* //회원가입 처리
    @PostMapping("/user/signUpPage")
    public String execSignUp(MemberDTO memberDTO){
        System.out.println("signUp 진입");
        System.out.println(memberDTO.getEmail());
        System.out.println(memberDTO.getPassword());

        Member member = memberService.joinUser(memberDTO);

        if(member != null){

        }else{

        }
        return "dssd";
    }*/

    /*@PostMapping("/user/verifyemail")
    public String verifyemail(@RequestBody String email){
        System.out.println("verifyemail-Controller 진입");
        //String message = memberService.verifyemail(email);
            return "message";
    }*/

}

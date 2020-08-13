package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.web.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    //회원가입 처리
    @PostMapping("/user/signUp")
    public void execSignUp(@RequestBody @Valid MemberRequestDTO memberRequestDTO, HttpSession session){
        System.out.println("signUp 진입");

        memberService.joinUser(memberRequestDTO);

    }



}

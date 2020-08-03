package com.cartrapido.main.web;

import com.cartrapido.main.dto.MemberDTO;
import com.cartrapido.main.service.member.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AllArgsConstructor
@Controller
public class IndexController {

    private MemberService memberService;
    
    //메인 페이지, zipcode입력 페이지
    @GetMapping("/")
    public String index(){
        return "firstPage";
    }

    //로그인 페이지
    @GetMapping("/user/loginPage")
    public String loginPage(){
        return "loginPage";
    }

    //회원가입 페이지
    @GetMapping("/user/signUpPage")
    public String signUpPage(@RequestParam("address_line_1") String address, Model model){
        model.addAttribute("address",address);
        return "signUpPage";
    }

    //회원가입 처리
    @PostMapping("/user/signUpPage")
    public String execSignup(MemberDTO memberDTO){
        memberService.joinUser(memberDTO);
        return "redirect:/user/loginSuccess";
    }

    @GetMapping("/user/loginSuccess")
    public String dispLoginResult(){
        return "loginSuccess";
    }

    //어드민 페이지
    @GetMapping("/admin")
    public String dispAdmin(){
        return "/adminPage";
    }

    @GetMapping("/user/denied")
    public String userDenied(){
        return "/errorPage";
    }



}

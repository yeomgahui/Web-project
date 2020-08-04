package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.dto.MemberDTO;
import com.cartrapido.main.service.member.MemberService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;
    private final MemberService memberService;


    //메인 페이지, zipcode입력 페이지
    @GetMapping("/")
    public String index(Model model){
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null){
            model.addAttribute("userName", user.getName());
            System.out.println("구글 로그인 성공");
            return "redirect:/user/loginSuccess";
        }
        return "firstPage";
    }

    //로그인 페이지
    @GetMapping("/user/loginPage")
    public String loginPage(){
        return "loginPage";
    }

    //회원가입 페이지
    @GetMapping("/user/signUpPage")
    public String signUpPage(@RequestParam String address, Model model,HttpSession session){
        System.out.println("signUpPage 진입");

        model.addAttribute("address",address);
        session.setAttribute("address",address);
        return "signUpPage";
    }
    @PostMapping("/user/signUpPage")
    public String execSignup(MemberDTO memberDTO){
        System.out.println("signUp 진입");
        memberService.joinUser(memberDTO);
        return "redirect:/user/loginSuccess";
    }


    @GetMapping("/user/loginSuccess")
    public String dispLoginResult(Model model,HttpSession session){
        model.addAttribute("address",session.getAttribute("address"));
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

    //로그아웃 결과 페이지
    @GetMapping("/user/logout/result")
    public String dispLogout() {
        httpSession.invalidate();
        return "redirect:/";
    }


}

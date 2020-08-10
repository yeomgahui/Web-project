package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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

        return "firstPage";
    }

    //로그인 페이지
    @GetMapping("/user/loginPage")
    public String loginPage(){
        System.out.println("loginPage 진입");
        return "loginPage.html";
    }

    //회원가입 페이지
    @GetMapping("/user/signUpPage")
    public String signUpPage(@RequestParam String address, Model model,HttpSession session){
        System.out.println("signUpPage 진입");
        model.addAttribute("address",address);
        session.setAttribute("address",address);
        return "signUpPage";
    }

    //회원가입 처리
    @PostMapping("/user/signUpPage")
    public String execSignUp(@ModelAttribute MemberDTO memberDTO){
        System.out.println("signUp 진입");
        /*System.out.println(memberDTO.getEmail());
        System.out.println(memberDTO.getPassword());*/

        Member member = memberService.joinUser(memberDTO);

        return "redirect:/user/loginPage";
    }



    @GetMapping("/user/loginSuccess")
    public String dispLoginResult(Model model,HttpSession session){
        System.out.println("loginSuccess 진입");
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user != null){
            model.addAttribute("userName",user.getName());
            model.addAttribute("userEmail",user.getEmail());
            model.addAttribute("userAddress",user.getAddress());
        }
        model.addAttribute("address",session.getAttribute("address"));
        return "loginSuccess";
    }

    @GetMapping("/clientMain")
    public String clientWeb(Model model,HttpSession session) {
        System.out.println("clientMain 진입");
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        session.setAttribute("userName",user.getName());
        session.setAttribute("userEmail",user.getEmail());
        session.setAttribute("userAddress",user.getAddress());

        return "/clientWebBody/clientMain";
    }


    @GetMapping("/user/loginFail")
    public String loginFail(){
        return "loginFail.html";
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

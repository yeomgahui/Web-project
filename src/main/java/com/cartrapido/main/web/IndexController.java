package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.MemberService;
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


    //메인 페이지, zipcode입력 페이지
    @GetMapping("/")
    public String index(Model model){
        System.out.println("첫페이지");
        return "/firstPage";
    }

    //로그인 페이지
    @GetMapping("/user/loginPage")
    public String loginPage(){
        System.out.println("loginPage 진입");
        return "loginPage.html";
    }

    @PostMapping("/user/loginPage")
    public String loginPage2(){
        System.out.println("loginPage 진입");
        return "loginPage.html";
    }

    //회원가입 페이지
    @PostMapping("/user/signUpPage")
    public String signUpPage(@RequestParam String address, Model model,HttpSession session){
        System.out.println("signUpPage 진입");
        model.addAttribute("address",address);
        session.setAttribute("address",address);
        return "signUpPage.html";
    }


    @GetMapping("/clientMain")
    public String clientWeb(Model model,HttpSession session) {
        System.out.println("clientMain 진입");
        /* username session duplicate error
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        session.setAttribute("userName",user.getName());
        session.setAttribute("userEmail",user.getEmail());
        session.setAttribute("userAddress",user.getAddress());*/

        return "/clientWebBody/clientMain.html";
    }

    @GetMapping("/shopperMain")
    public String shopperWeb(Model model,HttpSession session) {
        System.out.println("shopperMain 진입");
        /* username session duplicate error
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        session.setAttribute("userName",user.getName());
        session.setAttribute("userEmail",user.getEmail());
        session.setAttribute("userAddress",user.getAddress());*/

        return "/shopperWebBody/firstPage.html";
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

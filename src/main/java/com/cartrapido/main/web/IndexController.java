package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
<<<<<<< HEAD
import com.cartrapido.main.service.MemberService;
=======
import com.cartrapido.main.domain.Member;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.web.dto.MemberDTO;
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.ModelAttribute;
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

<<<<<<< HEAD
=======
    private final MemberService memberService;


>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280

    //메인 페이지, zipcode입력 페이지
    @GetMapping("/")
    public String index(Model model){
<<<<<<< HEAD
        System.out.println("첫페이지");
        return "/firstPage";
=======

        return "firstPage";
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
    }

    //로그인 페이지
    @GetMapping("/user/loginPage")
    public String loginPage(){
        System.out.println("loginPage 진입");
        return "loginPage.html";
    }

<<<<<<< HEAD
    @PostMapping("/user/loginPage")
    public String loginPage2(){
        System.out.println("loginPage 진입");
        return "loginPage.html";
    }

    //회원가입 페이지
    @PostMapping("/user/signUpPage")
=======
    //회원가입 페이지
    @GetMapping("/user/signUpPage")
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
    public String signUpPage(@RequestParam String address, Model model,HttpSession session){
        System.out.println("signUpPage 진입");
        model.addAttribute("address",address);
        session.setAttribute("address",address);
<<<<<<< HEAD
        return "signUpPage.html";
    }


    @GetMapping("/clientMain")
    public String clientWeb(Model model,HttpSession session) {
        System.out.println("clientMain 진입");
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        session.setAttribute("userName",user.getName());
        session.setAttribute("userEmail",user.getEmail());
        session.setAttribute("userAddress",user.getAddress());

        return "/clientWebBody/clientMain.html";
    }

    @GetMapping("/shopperMain")
    public String shopperWeb(Model model,HttpSession session) {
        System.out.println("shopperMain 진입");
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        session.setAttribute("userName",user.getName());
        session.setAttribute("userEmail",user.getEmail());
        session.setAttribute("userAddress",user.getAddress());

        return "/shopperTest.html";
    }


=======
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
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
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

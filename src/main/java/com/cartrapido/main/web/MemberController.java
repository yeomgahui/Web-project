package com.cartrapido.main.web;

<<<<<<< HEAD
import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.web.dto.MemberRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
=======
import com.cartrapido.main.domain.Member;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.web.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

<<<<<<< HEAD
    //회원가입 처리
    @PostMapping("/user/signUp")
    public void execSignUp(@RequestBody @Valid MemberRequestDTO memberRequestDTO, HttpSession session){
        System.out.println("signUp 진입");

        memberService.joinUser(memberRequestDTO);

    }


=======
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
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280

}

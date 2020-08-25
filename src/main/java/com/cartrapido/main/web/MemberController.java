package com.cartrapido.main.web;

import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.repository.MemberRepository;
import com.cartrapido.main.service.MemberService;
import com.cartrapido.main.web.dto.MemberRequestDTO;
import com.cartrapido.main.web.dto.MemberUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    private HttpSession httpSession;
    //회원가입 처리
    @PostMapping("/user/signUp")
    public void execSignUp(@RequestBody @Valid MemberRequestDTO memberRequestDTO, HttpSession session){
        System.out.println("signUp 진입");

        memberService.joinUser(memberRequestDTO);

    }
    //마이페이지 수정시 비밀번호 확인 부분
    @PostMapping("/checkPwd")
    public String checkPwd(@RequestParam(name="password") String password,HttpSession session){
        System.out.println("checkPwd 입장");
        System.out.println(password);
        SessionUser user = (SessionUser) session.getAttribute("user");
        String email = user.getEmail();

        String message = null;
        if(memberService.checkPwd(email, password)){
            message = "ok";
        }else{
            message = "notOk";
        }

        return message;
    }

    //회원정보 수정
    @PostMapping("/myPageUpdate")
    public void myPageUpdate(@RequestBody @Valid MemberUpdateRequestDTO updateRequestDTO,HttpSession session){
        SessionUser user = (SessionUser) session.getAttribute("user");
        String email = user.getEmail();

        memberService.updateMyPage(email, updateRequestDTO);
    }

}

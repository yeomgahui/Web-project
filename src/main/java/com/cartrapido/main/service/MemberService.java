package com.cartrapido.main.service;


import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.repository.MemberRepository;
import com.cartrapido.main.domain.Role;
import com.cartrapido.main.exception.ValidCustomException;
import com.cartrapido.main.web.dto.MemberRequestDTO;
import com.cartrapido.main.web.dto.MemberUpdateRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;
    private HttpSession httpSession;

    private HttpServletRequest httpServletRequest;


    @Transactional
    public String joinUser(MemberRequestDTO memberRequestDTO) {

        System.out.println("joinUser진입");

        System.out.println(memberRequestDTO.getRole());

        verifyDuplicateEmail(memberRequestDTO.getEmail());
        String tempPwd = memberRequestDTO.getPassword();


        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pwd = passwordEncoder.encode(memberRequestDTO.getPassword());

       MemberRequestDTO memberRequestDTO1 =  MemberRequestDTO.builder()
                .name(memberRequestDTO.getName())
                .email(memberRequestDTO.getEmail())
                .password(pwd)
                .role(memberRequestDTO.getRole())
                .address(memberRequestDTO.getAddress())
                .build();

        Member member = memberRepository.save(memberRequestDTO1.toEntity());

        //회원가입 성공시 바로 로그인 처리
        //authWithHttpServletRequest(httpServletRequest, memberRequestDTO.getEmail(), tempPwd);
        return "redirect:/clientMain";
    }

    //아이디 중복확인 메서드
    private void verifyDuplicateEmail(String email){

        if(memberRepository.findByEmail(email).isPresent()){
            throw new ValidCustomException("이미 사용중인 이메일주소입니다", "email");
        }
    }

    @Transactional
    public String verifyEmail(String email){

        if(memberRepository.findByEmail(email).isPresent()){
            char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
            StringBuilder sb = new StringBuilder("");
            Random rn = new Random();
            for( int i = 0 ; i < 6 ; i++ ){
                sb.append( charaters[ rn.nextInt( charaters.length ) ] );
            }
            Member member = memberRepository.findByEmail(email).get();
            String tempPwd = sb.toString();
            System.out.println("임시 비밀번호 생성"+ tempPwd);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            member.update(passwordEncoder.encode(tempPwd));

            return tempPwd;
        }
        return null;
    }

    //자동로그인
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        System.out.println("자동로그인 메서드 진입");
        try {
            request.login(username, password);
            System.out.println("request.login()");
        } catch (ServletException e) {
            System.out.println(e);
        }
    }

    //비밀번화 확인
    public Boolean checkPwd(String email, String temppwd){
        Optional<Member> member = memberRepository.findByEmail(email);
        String pwd = null;
        if(member.isPresent()){
            pwd = member.get().getPassword();
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(temppwd, pwd);
    }

    @Transactional
    public void updateMyPage(String email, MemberUpdateRequestDTO requestDTO){
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id"+ email));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pwd = passwordEncoder.encode(requestDTO.getPassword());

        System.out.println("바꿀 주소 ="+requestDTO.getAddress());
        System.out.println("바꿀 이름 ="+requestDTO.getName());


        Member userEntity =  member.update(requestDTO.getName(), requestDTO.getAddress(), pwd);
        httpSession.setAttribute("user", new SessionUser(userEntity));

    }
    @Transactional
    public void deleteUser(String email){
        Member member= memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. email ="+email ));
        memberRepository.delete(member);
    }




    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        System.out.println("loadUserByUserName 진입");
        Optional<Member> userEntityWrapper = memberRepository.findByEmail(userEmail);
        Member userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        httpSession.setAttribute("user", new SessionUser(userEntity));

        if (("admin@example.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getKey()));
        } else  if(userEntity.getRole() == Role.USER){
            authorities.add(new SimpleGrantedAuthority(Role.USER.getKey()));
        }else if(userEntity.getRole() ==Role.SHOPPER){
            authorities.add(new SimpleGrantedAuthority(Role.SHOPPER.getKey()));
        }

        UserDetails details = new User(userEntity.getEmail(), userEntity.getPassword(), authorities);

        return details;
    }

}
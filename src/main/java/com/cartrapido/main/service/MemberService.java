package com.cartrapido.main.service;


import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.repository.MemberRepository;
import com.cartrapido.main.domain.Role;
import com.cartrapido.main.web.dto.MemberDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
    private MemberRepository memberRepository;
    private HttpSession httpSession;

    /*@Transactional(readOnly = true)
    public List<MemberListResponseDto> findAllMember(){
        return memberRepository.findAllMember().stream()
                .map(MemberListResponseDto::new)
                .collect(Collectors.toList());
    }*/

    /*@Transactional
    public MemberResponseDto verifyemail(String email) {
        System.out.println("verifyemail 진입");

        System.out.println(email);
        //유효성 검사
*//*
        System.out.println(memberRepository.findByEmail(email).isPresent());
*//*

        Member entity = memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. email="+ email));
        return new MemberResponseDto(entity);
        *//*if(memberRepository.findByEmail(email).isPresent()){
            System.out.println("email 있다.");

            return "notOk";
        }else{
            System.out.println("email 없다.");
            return "ok";
        }

    }
*/
    @Transactional
    public Member joinUser(MemberDTO memberDTO) {
        System.out.println("joinUser진입");
        System.out.println(memberDTO.getEmail());
        System.out.println(memberDTO.getPassword());

        //비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));


        Member member = memberRepository.save(memberDTO.toEntity());
        httpSession.setAttribute("user", new SessionUser(member));

        return member;
    }




    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        System.out.println("loadUserByUserName 진입");
        Optional<Member> userEntityWrapper = memberRepository.findByEmail(userEmail);
        System.out.println("이메일찾기");
        Member userEntity = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (("admin@example.com").equals(userEmail)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getKey()));
        } else if(userEntity.getRole().equals("USER")) {
            System.out.println("authorities에 넣어준다.");
            authorities.add(new SimpleGrantedAuthority(Role.USER.getKey()));
            System.out.println("authorities에 넣어줬다.");
        }
        UserDetails details = new User(userEntity.getEmail(), userEntity.getPassword(), authorities);

        return details;
    }

}
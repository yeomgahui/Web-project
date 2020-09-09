package com.cartrapido.main.domain.entity;

import com.cartrapido.main.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Setter
public class Member implements UserDetails {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;

        @Column(nullable = false)
        private String email;

        private String address;

        private String password;

        //enable이 true이면 로그인 가능 /false 이면 로그인 불가능
        @Column(columnDefinition = "boolean default true")
        private Boolean enable;

        private int point;

        private int score;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private Role role;

        @Builder
        public Member(String name, String email,String address,String password, Role role){
            this.name = name;
            this.email = email;
            this.address = address;
            this.password = password;
            this.enable = true;
            this.point = 3000;
            this.role = role;
        }

        public Member update(String name,String address){
            this.name = name;
            this.address = address;

            return this;
        }

        public Member update(String name){
            this.name = name;
            return this;
        }
        public Member update(String name,String address, String pwd){
            this.name = name;
            this.address = address;
            this.password = pwd;
            return this;
        }
        public Member updatePwd(String pwd){
            this.password = pwd;
            return this;
        }
        public Member updatePoint(int score){
            this.score = score;
            return this;
        }
        public Member updateScore(int point){
            this.point = point;
          return this;
    }
        public Member enableSet(boolean enable){
            this.enable = enable;
            return this;
        }


        public String getRoleKey(){
            return this.role.getKey();
        }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            Set<GrantedAuthority> roles = new HashSet<>();
            roles.add(new SimpleGrantedAuthority(role.getKey()));
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
            //만료되었는지 확인
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
            //계정 잠금 확인
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
            //패스워드 만료 확인
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enable;
    }
}

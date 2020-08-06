package com.cartrapido.main.config.auth;

import com.cartrapido.main.domain.Role;
import com.cartrapido.main.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private MemberService memberService;

    public PasswordEncoder passwordEncoder() {
        System.out.println("비밀번호1");
        return new BCryptPasswordEncoder();
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("비밀번호2");
        auth.inMemoryAuthentication().withUser("").password();
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure 진입");
        http
                .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("!/h2-console/**"))
                .and()
                .headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src'self'")).frameOptions().disable()
                //.csrf().disable()
                //.headers().frameOptions().disable()
                .and()
                .csrf()
                .ignoringAntMatchers("/user/signUpPage")
                .and()
                .authorizeRequests()
                .antMatchers("/","/user/loginPage","/login","/user/signUpPage", "/css/**","/user/verifyemail", "/images/**","/h2-console/**", "/js/**","/user/loginFail",  "/profile").permitAll()
                .antMatchers("/api/v1/**","/user/loginSuccess").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .formLogin()
                //.loginPage("/user/loginPage")
                .loginProcessingUrl("/login")
                //.successHandler(new LoginSuccessHandler())
               // .usernameParameter("userName")
                //.passwordParameter("password")
                .failureUrl("/user/loginFail")
                .and()
                .oauth2Login()
                .successHandler(new LoginSuccessHandler())
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("비밀번호2");
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }*/

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("비밀번호2");
        auth.inMemoryAuthentication()
                .withUser("user").password("{id}password").roles("USER")
                .and()
                .withUser("admin").password("password").roles("ADMIN");
    }*/
}

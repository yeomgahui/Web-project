package com.cartrapido.main.config.auth;

import com.cartrapido.main.domain.Role;
import com.cartrapido.main.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final MemberService memberService;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }


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
                .authorizeRequests()
                .antMatchers("/","/user/loginPage","/login","/logout","/user/signUpPage","/user/signUp", "/css/**","/user/verifyemail", "/images/**","/h2-console/**","/mail", "/js/**","/user/loginFail",  "/profile").permitAll()
                .antMatchers("/api/v1/**","/clientMain").hasRole(Role.USER.name())
                .antMatchers("/shopperTest").hasRole(Role.SHOPPER.name())
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .formLogin()
                .loginPage("/user/loginPage")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .and()
                .oauth2Login()
                .loginPage("/user/loginPage")
                .successHandler(new LoginSuccessHandler())
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}

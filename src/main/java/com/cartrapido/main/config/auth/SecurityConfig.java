package com.cartrapido.main.config.auth;

import com.cartrapido.main.domain.Role;
import com.cartrapido.main.service.MemberService;
import lombok.RequiredArgsConstructor;
<<<<<<< HEAD
import org.springframework.context.annotation.Configuration;
=======
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

<<<<<<< HEAD
import javax.sql.DataSource;

@Configuration
=======
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
<<<<<<< HEAD
    private final MemberService memberService;

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

=======
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
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280

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
<<<<<<< HEAD
                .authorizeRequests()
                .antMatchers("/","/user/loginPage","/login","/logout","/user/signUpPage","/user/signUp", "/css/**","/user/verifyemail", "/images/**","/h2-console/**","/mail", "/js/**","/user/loginFail",  "/profile").permitAll()
                .antMatchers("/api/v1/**","/clientMain").hasRole(Role.USER.name())
                .antMatchers("/shopperTest").hasRole(Role.SHOPPER.name())
=======
                .csrf()
                .ignoringAntMatchers("/user/signUpPage")
                .and()
                .authorizeRequests()
                .antMatchers("/","/user/loginPage","/login","/user/signUpPage", "/css/**","/user/verifyemail", "/images/**","/h2-console/**", "/js/**","/user/loginFail",  "/profile").permitAll()
                .antMatchers("/api/v1/**","/user/loginSuccess").hasRole(Role.USER.name())
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .and()
                .formLogin()
<<<<<<< HEAD
                .loginPage("/user/loginPage")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .and()
                .oauth2Login()
                .loginPage("/user/loginPage")
=======
                //.loginPage("/user/loginPage")
                .loginProcessingUrl("/login")
                //.successHandler(new LoginSuccessHandler())
               // .usernameParameter("userName")
                //.passwordParameter("password")
                .failureUrl("/user/loginFail")
                .and()
                .oauth2Login()
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
                .successHandler(new LoginSuccessHandler())
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
<<<<<<< HEAD
=======

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
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
}

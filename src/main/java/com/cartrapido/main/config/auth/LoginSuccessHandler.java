package com.cartrapido.main.config.auth;

<<<<<<< HEAD
import com.cartrapido.main.config.auth.dto.SessionUser;
import com.cartrapido.main.domain.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
=======
import org.springframework.security.core.Authentication;
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
<<<<<<< HEAD
import java.util.Set;
=======
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        System.out.println("로그인 석세스핸들러");
        session.setAttribute("username", authentication.getName());
<<<<<<< HEAD
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roles.contains("ROLE_USER")){
            response.sendRedirect("/clientMain");
        }else if(roles.contains("ROLE_SHOPPER")){
            response.sendRedirect("/shopperMain");
        }
=======
        response.sendRedirect("/user/loginSuccess");
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
    }
}


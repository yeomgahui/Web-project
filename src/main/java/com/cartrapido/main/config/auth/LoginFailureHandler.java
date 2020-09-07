package com.cartrapido.main.config.auth;

import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final String DEFAULT_FAILURE_URL="/user/loginPage?error=true";


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = null;
        System.out.println("로그인 실패 핸들러");
        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errorMessage ="아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
        }else{
            errorMessage ="관리자에 의해 이용이 정지된 아이디 입니다.";
        }
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(request,response);
    }
}

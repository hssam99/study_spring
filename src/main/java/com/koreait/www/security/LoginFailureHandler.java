package com.koreait.www.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        log.info("❌login failure");
        // 로그인 시도 id
        String failEmail = request.getParameter("email");
        String errorMessage = "";

        log.info(">>> failure Exception : {}", exception.getMessage().toString());
        if( exception instanceof BadCredentialsException){
            errorMessage="아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage="내부 시스템 문제로 인해 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }else{
            errorMessage="알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }

        log.info(">>> errorMessage : {}", errorMessage);

        request.setAttribute("failEmail", failEmail);
        request.setAttribute("errorMessage", errorMessage);

        request.getRequestDispatcher("/user/login?error").forward(request, response);
    }
}
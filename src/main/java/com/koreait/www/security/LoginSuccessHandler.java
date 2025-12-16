package com.koreait.www.security;

import com.koreait.www.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    // lastLogin 업데이트, 로그인 성공 후 처리 로직 구현
    // 직전 경로로 돌아가기
    private final UserDAO udao;

    // redirect 경로로 이동하는 역할
    private RedirectStrategy redStr = new DefaultRedirectStrategy();

    // 직전 경로의 url 정보를 가지고 있는 객체 (세션의 캐쉬 정보)
    private RequestCache reqCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        // lastLogin 업데이트
        // authentication -> getName() -> return username
        String authEmail = authentication.getName();
        int isOk = udao.updateLastLogin(authEmail);
        System.out.println("LoginSuccessHandler lastLogin update: " + ((isOk>0)? "✅success":"❌fail"));

        // 로그인을 성공하면 기존 실패했던 기록을 삭제
        HttpSession ses = req.getSession();
        if(ses==null){
            return;
        }else{
            ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

        // 로그인 직전 URL 정보가 있으면 해당 URL로 이동
        String authUrl = "/";
        SavedRequest savedRequest = reqCache.getRequest(req, resp);
        log.info("✅savedRequest: {}", savedRequest);
        redStr.sendRedirect(req, resp,
                savedRequest != null? savedRequest.getRedirectUrl():authUrl);
    }
}

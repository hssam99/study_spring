package com.koreait.www.security;

import com.koreait.www.domain.UserVO;
import com.koreait.www.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthUserService implements UserDetailsService {

    private final UserDAO udao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기서 DB에서 사용자 정보 조회해서 UserDetails로 반환
        // username + password -> 인증용 토큰 생성
        // username -> DB에서 조회 -> UserDetails 객체 생성 후 반환
        // User 객체 <username, password, authorities> -> UserDetails 객체로 리턴
        UserVO user = udao.selectByEmail(username);

        if(user == null){
            log.info("❌ User not found: {}", username);
            throw new UsernameNotFoundException(username);
        }
        log.info("✅ User found: {}", username);
        user.setAuthList(udao.selectAuthByEmail(username));
        log.info(">>> 로그인 유저 : {}", user);
        return new AuthUser(user);
    }
}
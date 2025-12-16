package com.koreait.www.security;

import com.koreait.www.domain.UserVO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class AuthUser extends User {

    private static final long serialVersionUID = 1L;

    @Getter
    private UserVO userVO;

    public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public AuthUser(UserVO userVO) {
        super(userVO.getEmail(),
                userVO.getPw(),
                userVO.getAuthList().stream()
                        .map(auth -> new SimpleGrantedAuthority(auth.getAuth()))
                        .collect(Collectors.toList()));
        this.userVO = userVO;
    }
}

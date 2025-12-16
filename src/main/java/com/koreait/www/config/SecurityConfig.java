package com.koreait.www.config;

import com.koreait.www.repository.UserDAO;
import com.koreait.www.security.CustomAuthUserService;
import com.koreait.www.security.LoginFailureHandler;
import com.koreait.www.security.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDAO userDAO;

    public SecurityConfig(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // 비밀번호 암호화 객체 빈 생성 PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // SuccessHandler 객체 빈 생성 -> 사용자 커스텀 객체
    @Bean
    public AuthenticationSuccessHandler authSuccessHandler(){
        return new LoginSuccessHandler(userDAO);
    }

    //FailureHandler 객체 빈 생성 -> 사용자 커스텀 객체
    @Bean
    public AuthenticationFailureHandler authFailureHandler(){
        return new LoginFailureHandler();
    }

    @Bean
    public UserDetailsService customDetailsService(UserDAO userDAO) {
        return new CustomAuthUserService(userDAO);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 인증용 객체 생성 매니저
        auth.userDetailsService(customDetailsService(userDAO))
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 권한에 따른 주소 매핑 설정
        // 로그인 로그아웃 구성정보 설정
        // csrf 공격에 대한 설정
        // security에서는 기본적으로 활성화 되어있는 기능
        // 클라이언트 -> 서버 요청을 보낼 때 csrf 토큰을 함께 전송
        // get mapping은 토큰 전송이 불필요 (서버에서 일방적으로 데이터를 보내는 mapping)
        // post, put, delete mapping은 토큰 전송이 필수 (서버에 데이터를 변경하는 mapping)

//        http.csrf().disable(); // 공격에 대한 방어 시스템 풀기, 개발 중에는 비활성화
        CharacterEncodingFilter encoding = new CharacterEncodingFilter();
        encoding.setEncoding("UTF-8");
        encoding.setForceEncoding(true);
        http.addFilterBefore(encoding, CsrfFilter.class);

        // 권한에 따른 승인 요청
        // antMatchers : 접근을 허용하는 경로
        // hasRole("권한") : 해당 권한 확인 -> ROLE_ 접두사가 자동으로 붙음
        // authenticatd() : 인증된 사용자만 가능한 경로
        // permitAll() : 누구나 접근 가능한 경로
        http.authorizeRequests()
                .antMatchers("/user/list").hasRole("ADMIN")
                .antMatchers("/",
                        "/board/list", "/board/detail",
                        "/user/login", "/user/register", "/user/insert",
                        "/upload/**",  "/resources/**", "/comment/list/**").permitAll()
                .anyRequest().authenticated();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("pw")
                .loginPage("/user/login")
                .successHandler(authSuccessHandler())
                .failureHandler(authFailureHandler ());

        http.logout()
                .logoutUrl("/user/logout")                        // 이 URL로 요청 오면
                .invalidateHttpSession(true)                        // 세션 삭제
                .deleteCookies("JSESSIONID")     // 쿠키 삭제
                .logoutSuccessUrl("/");                           // 완료 후 "/" 로 이동
    }
}

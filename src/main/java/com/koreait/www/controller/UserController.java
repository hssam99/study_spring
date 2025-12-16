package com.koreait.www.controller;

import com.koreait.www.domain.UserAuthVO;
import com.koreait.www.domain.UserVO;
import com.koreait.www.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/*")
@Controller
public class UserController {
    private final UserService usv;
    private final PasswordEncoder pwEncoder;

    private void logout(HttpServletRequest req, HttpServletResponse resp) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(req, resp, auth);
    }

    @GetMapping("/register")
    public void register(){
    }

    @PostMapping("/insert")
    public String insert(UserVO user){
        log.info("✅user register....{}", user);
        user.setPw(pwEncoder.encode(user.getPw()));
        log.info("✅user insert....{}", user);
        int isOk = usv.insert(user);
        log.info("회원가입: {}", (isOk>0)? "✅success":"❌fail");
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public void login(){}

    @PostMapping("/login")
    public String loginPost(HttpServletRequest request, RedirectAttributes re){
        // 로그인 실패시 메시지 전송을 위해 사용
        log.info(">> failEmail: {}", request.getAttribute("failEmail"));
        log.info(">> errorMessage: {}", request.getAttribute("errorMessage"));

        re.addFlashAttribute("failEmail", request.getAttribute("failEmail"));
        re.addFlashAttribute("errorMessage", request.getAttribute("errorMessage"));
        return "redirect:/user/login";
    }

    @GetMapping("/list")
    public void list(Model model){
        List<UserVO> userList = usv.getList();

        for(UserVO user : userList) {
            user.setAuthList(usv.selectAuthByEmail(user.getEmail()));
        }
        model.addAttribute("userList", userList);
    }

    @GetMapping("/modify")
    public String modify(){
        // user의 객체를 DB에서 가져와서(email) modify.jsp로 전송
        // principal 객체를 이용해도 됨

        return "user/modify";
    }

    @PostMapping("/modify")
    public String modify(UserVO user, RedirectAttributes re,
                         HttpServletRequest req, HttpServletResponse resp){
        int isOk = 0;
        if(user.getPw().isEmpty() || user.getPw().length() == 0){
            isOk = usv.modifyPwEmpty(user);
        }else{
            // 비밀번호 암호화
            user.setPw(pwEncoder.encode(user.getPw()));
            isOk = usv.modify(user);
        }
        log.info("회원정보 수정: {}", (isOk > 0) ? "✅success" : "❌fail");
        // 회원정보 수정 완료 후 -> 로그아웃 -> 재로그인 (변경된 환경을 다시 셋팅)
        if(isOk>0){
            logout(req, resp);
            re.addFlashAttribute("modify_msg", "ok" );
            return "redirect:/";
        }else{
            re.addFlashAttribute("modify_msg", "fail");
            return "redirect:/user/modify";
        }
    }

    @GetMapping("/delete")
    public String delete(RedirectAttributes re, Principal principal,
            HttpServletRequest req, HttpServletResponse resp){
        String email = principal.getName();
        int isOk= usv.delete(email);
        log.info("회원탈퇴: {}", (isOk>0) ? "✅success" : "❌fail");
        if(isOk>0){
            logout(req, resp);
            re.addFlashAttribute("delete_msg", "ok");
            return "redirect:/";
        }else{
            re.addFlashAttribute("delete_msg", "fail");
            return "redirect:/user/modify";
        }
    }
}

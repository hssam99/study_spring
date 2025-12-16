package com.koreait.www.service;

import com.koreait.www.domain.UserAuthVO;
import com.koreait.www.domain.UserVO;
import com.koreait.www.repository.UserDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserDAO udao;

    @Transactional
    @Override
    public int insert(UserVO user) {
        int isOk = udao.insert(user);
        if(isOk >0){
            isOk *= udao.insertAuth(user.getEmail());
        }
        return isOk;
    }

    @Override
    public int modifyPwEmpty(UserVO user) {
        return udao.modifyPwEmpty(user);
    }

    @Override
    public int modify(UserVO user) {
        return udao.modify(user);
    }

    @Transactional
    @Override
    public int delete(String email) {
        int isOk = udao.delete(email);
        isOk *= udao.authDelete(email);
        return isOk;
    }

    @Override
    public List<UserVO> getList() {
        // UserVO => authList => 두개의 값을 가져와야 함
        List<UserVO> userList =  udao.getList();
        for(UserVO user : userList){
            log.info("✅ user: {}", user);
        }
        return userList;
    }

    @Override
    public List<UserAuthVO> selectAuthByEmail(String email) {
        return udao.selectAuthByEmail(email);
    }

}

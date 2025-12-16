package com.koreait.www.service;

import com.koreait.www.domain.UserAuthVO;
import com.koreait.www.domain.UserVO;

import java.util.List;

public interface UserService {
    int insert(UserVO user);

    int modifyPwEmpty(UserVO user);

    int modify(UserVO user);

    int delete(String email);

    List<UserVO> getList();

    List<UserAuthVO> selectAuthByEmail(String email);
}

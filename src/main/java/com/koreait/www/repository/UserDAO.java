package com.koreait.www.repository;

import com.koreait.www.domain.UserAuthVO;
import com.koreait.www.domain.UserVO;

import java.util.List;

public interface UserDAO {

    int insert(UserVO user);

    int insertAuth(String email);

    UserVO selectByEmail(String username);

    List<UserAuthVO> selectAuthByEmail(String username);

    int updateLastLogin(String authEmail);

    int modifyPwEmpty(UserVO user);

    int modify(UserVO user);

    int delete(String email);

    List<UserVO> getList();

    int authDelete(String email);
}

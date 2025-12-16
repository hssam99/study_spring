package com.koreait.www.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    private String email;
    private String pw;
    private String name;
    private String regDate;
    private String lastLogin;
    private List<UserAuthVO> authList;
}

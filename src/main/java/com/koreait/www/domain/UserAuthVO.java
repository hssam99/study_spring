package com.koreait.www.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class UserAuthVO {
    private long id;
    private String email;
    private String auth;
}

package com.koreait.www.domain;


import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    private long cno;
    private long bno;
    private String writer;
    private String content;
    private String regDate;
}

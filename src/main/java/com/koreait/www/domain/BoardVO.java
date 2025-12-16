package com.koreait.www.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class BoardVO {
    private long bno;
    private String title;
    private String writer;
    private String content;
    private int isDel;
    private String regDate;
    private int readCount;
    private int cmtQty;
    private int fileQty;
}

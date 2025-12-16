package com.koreait.www.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileVO {
    private String uuid;
    private String saveDir;
    private String fileName;
    private String fileType;
    private long bno;
    private long fileSize;
    private String regDate;
}

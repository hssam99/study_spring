package com.koreait.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BoardFileDTO {
    private BoardVO board;
    private java.util.List<FileVO> fileList;
}

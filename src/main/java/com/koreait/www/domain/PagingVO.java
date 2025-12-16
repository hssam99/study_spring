package com.koreait.www.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class PagingVO {
    private int pageNo;
    private int qty;

    private  String type;
    private String keyword;

    public PagingVO(){
        this.pageNo = 1;
        this.qty = 10;
    }

    public PagingVO(int pageNo, int qty) {
        setPageNo(pageNo);
        setQty(qty);
    }

    // DB 에서 사용될 데이터 설정 (시작번지 구하기)
    // select * from board limit 번지, 개수 -> 0부터 시작
    // 1 => 0,10 / 2-> 10, 10 / 3-> 20, 10
    public int getPageStart(){
        return (this.pageNo -1) * this.qty;
    }

    // 복합 타입을 각각 검색으로 분할
    public String[] getTypeToArray(){
        return this.type==null ? new String[] {} : this.type.split("");
    }

}

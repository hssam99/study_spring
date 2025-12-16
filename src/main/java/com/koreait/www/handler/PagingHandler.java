package com.koreait.www.handler;

import com.koreait.www.domain.CommentVO;
import com.koreait.www.domain.PagingVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class PagingHandler {
    private int qty;                // 하단 페이지네이션 개수 ( 1 2 3 ... 10)
    private int startPage;          // 시작 페이지 번호
    private int endPage;            // 끝 페이지 번호
    private boolean prev, next;     // 이전, 다음 페이지 존재 여부

    private int totalCount;         // 총 게시물 수
    private PagingVO paging;      // 현재 페이지 정보

    private int realEndPage;        // 실제 끝 페이지 번호

    // 댓글 페이징을 위한 CommentList 값을 추가
    private List<CommentVO> cmtList;

    public PagingHandler(int totalCount, PagingVO pagingVO){
        this.qty = 10;
        this.paging = pagingVO;
        this.totalCount = totalCount;


        // 1. endPage 계산
        this.endPage = (int)(Math.ceil(pagingVO.getPageNo() / (double)qty)) * qty;

        // 2. startPage 계산
        this.startPage = this.endPage - (qty - 1);

        // 3. realEndPage 계산
        this.realEndPage = (int)(Math.ceil(totalCount / (double)pagingVO.getQty()));

        // 4. endPage 재계산
        if(this.endPage > this.realEndPage){
            this.endPage = this.realEndPage;
        }

        // 5. prev, next 계산
        this.prev = this.startPage > 1;
        this.next = this.endPage < this.realEndPage;
    }


    // 댓글 페이징을 위한 생성자
    public PagingHandler(int totalCount, PagingVO paging, List<CommentVO> cmtList){
        this(totalCount, paging);
        this.cmtList = cmtList;
    }

}

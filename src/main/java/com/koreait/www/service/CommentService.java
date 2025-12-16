package com.koreait.www.service;

import com.koreait.www.domain.CommentVO;
import com.koreait.www.domain.PagingVO;
import com.koreait.www.handler.PagingHandler;

import java.util.List;

public interface CommentService {
    int insert(CommentVO comment);

//    List<CommentVO> getCmtList(Long bno);

    PagingHandler getList(Long bno, PagingVO paging);

    int update(CommentVO comment);

    int delete(Long cno, Long bno);
}

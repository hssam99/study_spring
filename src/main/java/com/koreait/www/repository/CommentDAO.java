package com.koreait.www.repository;

import com.koreait.www.domain.CommentVO;
import com.koreait.www.domain.PagingVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentDAO {
    int insert(CommentVO comment);

//    List<CommentVO> getCmtList(Long bno);

    List<CommentVO> getList(@Param("bno") Long bno, @Param("paging") PagingVO paging);

    int getTotal(Long bno);

    int update(CommentVO comment);

    int delete(@Param("cno")Long cno, @Param("bno") Long bno);
}

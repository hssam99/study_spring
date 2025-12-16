package com.koreait.www.repository;

import com.koreait.www.domain.BoardVO;
import com.koreait.www.domain.PagingVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardDAO {
    int insert(BoardVO board);

    List<BoardVO> getList(PagingVO paging);

    BoardVO getDetail(long bno);

    int update(BoardVO board);

    int delete(long bno);

    int getTotalCount(PagingVO pvo);

    void upReadCount(Long bno);

    void updateCmtQty(@Param("bno") Long bno, @Param("amount") int amount);

    long getBno();

    int fileQtyUpdate(@Param("bno") long bno, @Param("size") int size);
}

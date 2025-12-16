package com.koreait.www.service;

import com.koreait.www.domain.BoardFileDTO;
import com.koreait.www.domain.BoardVO;
import com.koreait.www.domain.FileVO;
import com.koreait.www.domain.PagingVO;

import java.util.List;

public interface BoardService {

//    int insert(BoardVO board);

    List<BoardVO> getList(PagingVO paging);

    BoardFileDTO getDetail(long bno);

    int update(BoardFileDTO boardFileDTO);

    int delete(long bno);

    int getTotalCount(PagingVO pvo);

    void upReadCount(Long bno);

    int insert(BoardFileDTO boardFileDTO);

    int deleteFile(String uuid);

    FileVO getFile(String uuid);
}

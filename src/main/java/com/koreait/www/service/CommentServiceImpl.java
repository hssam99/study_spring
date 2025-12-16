package com.koreait.www.service;

import com.koreait.www.domain.CommentVO;
import com.koreait.www.domain.PagingVO;
import com.koreait.www.handler.PagingHandler;
import com.koreait.www.repository.BoardDAO;
import com.koreait.www.repository.CommentDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentDAO cdao;
    private final BoardDAO bdao;

    @Transactional
    @Override
    public int insert(CommentVO comment) {
        int result = cdao.insert(comment);
        if (result > 0) {
            bdao.updateCmtQty(comment.getBno(), 1);  // +1
        }
        return result;
    }

    @Override
    public PagingHandler getList(Long bno, PagingVO paging) {
        List<CommentVO> list = cdao.getList(bno, paging);
        int totalCount = cdao.getTotal(bno);
        PagingHandler ph = new PagingHandler(totalCount, paging, list);
        return ph;
    }

    @Override
    public int update(CommentVO comment) {
        return cdao.update(comment);
    }

    @Override
    public int delete(Long cno, Long bno) {
        int result = cdao.delete(cno, bno);
        if (result > 0) {
            bdao.updateCmtQty(bno, -1);  // -1
        }
        return result;
    }


//    @Override
//    public List<CommentVO> getCmtList(Long bno) {
//        return cdao.getCmtList(bno);
//    }
}

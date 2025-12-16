package com.koreait.www.service;

import com.koreait.www.domain.BoardFileDTO;
import com.koreait.www.domain.BoardVO;
import com.koreait.www.domain.FileVO;
import com.koreait.www.domain.PagingVO;
import com.koreait.www.repository.BoardDAO;
import com.koreait.www.repository.FileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
    private final BoardDAO bdao;
    private final FileDAO fdao;

    @Override
    public List<BoardVO> getList(PagingVO paging) {
        return bdao.getList(paging);
    }

    @Transactional
    @Override
    public BoardFileDTO getDetail(long bno) {
        BoardVO board = bdao.getDetail(bno);
        List<FileVO> fileList = fdao.getList(bno);
        BoardFileDTO boardFileDTO = new BoardFileDTO(board, fileList);
        return boardFileDTO;
    }

    @Transactional
    @Override
    public int update(BoardFileDTO boardFileDTO) {
        int isOk = bdao.update(boardFileDTO.getBoard());
        if(boardFileDTO.getFileList()==null){
            return isOk;
        }
        if(isOk>0){
            for(FileVO file : boardFileDTO.getFileList()){
                file.setBno(boardFileDTO.getBoard().getBno());
                isOk *= fdao.insert(file);
            }
        }
        isOk *= bdao.fileQtyUpdate(boardFileDTO.getBoard().getBno(), boardFileDTO.getFileList().size());
        log.info(">>> 파일 업로드: {} ", (isOk > 0) ? "✅성공" : "❌실패");
        return isOk;
    }

    @Override
    public int delete(long bno) {
        return bdao.delete(bno);
    }

    @Override
    public int getTotalCount(PagingVO pvo) {
        return bdao.getTotalCount(pvo);
    }

    @Override
    public void upReadCount(Long bno) {
        bdao.upReadCount(bno);
    }

    @Transactional
    @Override
    public int insert(BoardFileDTO boardFileDTO) {
        int isOk = bdao.insert(boardFileDTO.getBoard());

        if(boardFileDTO.getFileList()==null){
            return isOk;
        }

        if(isOk > 0 && boardFileDTO.getFileList() != null){
            long bno = bdao.getBno();
            for(FileVO file : boardFileDTO.getFileList()){
                file.setBno(bno);
                isOk *= fdao.insert(file);
            }
            isOk *= bdao.fileQtyUpdate(bno, boardFileDTO.getFileList().size());
            log.info(">>> 파일 업로드: {} ", (isOk > 0) ? "✅성공" : "❌실패");
        }
        return isOk;
    }

    @Override
    public int deleteFile(String uuid) {
        long bno = fdao.getFile(uuid).getBno();
        int isOk = fdao.deleteFile(uuid);
        if(isOk > 0){
            isOk *= bdao.fileQtyUpdate(bno, -1);
        }
        return isOk;
    }

    @Override
    public FileVO getFile(String uuid) {
        return fdao.getFile(uuid);
    }

}

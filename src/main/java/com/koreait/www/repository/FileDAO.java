package com.koreait.www.repository;

import com.koreait.www.domain.FileVO;

import java.util.List;

public interface FileDAO {
    int insert(FileVO file);

    List<FileVO> getList(long bno);

    int deleteFile(String uuid);

    FileVO getFile(String uuid);

    List<FileVO> getFilesByDate(String today);
}

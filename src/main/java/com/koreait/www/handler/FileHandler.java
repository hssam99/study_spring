package com.koreait.www.handler;


import com.koreait.www.domain.FileVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileHandler {
    private final String UPLOAD_PATH = "/Users/suminhong/web_0826_hsm/_myProject/_java/_fileUpload";

    // multipartfiles를 받아서 FileVO 리스트로 반환하는 메서드 작성 예정
    public List<FileVO> uploadFile(MultipartFile[] files){
        // fileVO 생성 + 파일 저장 + 이미지일 경우 썸네일 저장
        // 일반적으로 파일 저장시 날짜별로 폴더화 하여 업로드된 파일 관리
        List<FileVO> fileList = new ArrayList<FileVO>();
        LocalDate date = LocalDate.now();

        String today = date.toString();
        today = today.replace("-", File.separator);

        File folders = new File(UPLOAD_PATH, today); // 폴더 생성
        // mkdir : 폴더 생성 명령어  / mkdirs : 상위 폴더까지 생성 명령어
        if(!folders.exists()){
            folders.mkdirs();
        }

        // files를 가지고 fileVO 생성
        for(MultipartFile file : files){
            FileVO fileVO = new FileVO();
            fileVO.setSaveDir(today);
            log.info(">>> 업로드 파일명: {}", file.getOriginalFilename());
            String fileName = file.getOriginalFilename();
            fileVO.setFileName(fileName);
            fileVO.setFileSize(file.getSize());

            UUID uuid = UUID.randomUUID();
            fileVO.setUuid(uuid.toString());

            String fullFileName = uuid.toString() + "_" + fileName;
            File saveFile = new File(folders, fullFileName);

            try{
                file.transferTo(saveFile);

                if(isImageFile(saveFile)){
                    fileVO.setFileType("I");

                    // 썸네일 생성
                    File thumbNail = new File(folders, uuid.toString() + "_th_"+fileName);
                    Thumbnails.of(saveFile).size(100,100).toFile(thumbNail);
                }else {
                    fileVO.setFileType("F");  // 1. 이미지 아닐 때 타입 설정
                }
                fileList.add(fileVO);
            }catch (Exception e){
                log.info("error");
                e.printStackTrace();
            }
        }
        return fileList;
    }

    private boolean isImageFile(File saveFile) {
        try {
            String mimeType = new Tika().detect(saveFile);
            return mimeType.startsWith("image");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

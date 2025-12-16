package com.koreait.www.handler;

import com.koreait.www.domain.FileVO;
import lombok.extern.slf4j.Slf4j;

import java.io.File;


@Slf4j
public class FileRemoveHandler {

    // 저장 경로
    private final String DIR = "/Users/suminhong/web_0826_hsm/_myProject/_java/_fileUpload";


    public boolean deleteFile(FileVO file){

        ///Users/suminhong/web_0826_hsm/_myProject/_java/_fileUpload/2025/12/uuid_fileName
        ///Users/suminhong/web_0826_hsm/_myProject/_java/_fileUpload/2025/12/uuid_th_fileName

        boolean isDel = false;
        File fileDir = new File(DIR, file.getSaveDir());
        String removeFile = file.getUuid()+"_"+file.getFileName();
        File delFile = new File(fileDir, removeFile);
        String removeThFile = file.getUuid()+"_th_"+file.getFileName();
        File delThFile = new File(fileDir, removeThFile);

//        try{
//            if(delFile.exists()){
//                isDel = delFile.delete();
//                log.info(">>> 파일 삭제 완료: {}", delFile.getAbsolutePath());
//                if("I".equals(file.getFileType()) && delThFile.exists()){
//                    boolean isThDel = delThFile.delete();
//                    log.info(">>> 썸네일 삭제 완료: {}", delThFile.getAbsolutePath());
//                }
//            }
//
//        }catch (Exception e){
//            log.info(">>> 파일 삭제 중 오류 발생: {}", e.getMessage());
//            e.printStackTrace();
//        }

        return  isDel;
    }


}

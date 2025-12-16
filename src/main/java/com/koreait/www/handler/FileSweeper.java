package com.koreait.www.handler;

import com.koreait.www.domain.FileVO;
import com.koreait.www.repository.FileDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Slf4j
@Component
@RequiredArgsConstructor
public class FileSweeper {
//    매일 등록된 시간에 파일 정리 -> 스케줄러가 실행
//    화면 상에서는 삭제되었지만, 실제 폴더에는 남아있는 파일들을 정리
//    스케줄 기록 방식 cron 방식
//    cron 방식 : 초 분 시 일 월 요일 년도(생략가능)
//    cron = " 59 59 23 * * * " -> 매일 23시 59분 59초에 실행

//    DB에서 오늘 날짜의 파일을 모두 가져오기
//    파일 객체로 생성

//    1. DB 접근
    private final FileDAO fdao;

//    2. 파일 경로

    private final String DIR = "/Users/suminhong/web_0826_hsm/_myProject/_java/_fileUpload/";

    @Scheduled(cron = "00 35 12 * * *")
    public void sweepFiles() {
        log.info(">>> FileSweeper 스케줄러 실행됨 :  {}", LocalDateTime.now());


        // 오늘 날짜 파일 리스트 가져오기
        LocalDate now = LocalDate.now(); // 2025-12-31
        String today = now.toString();
        today = today.replace("-", File.separator); // 2025/12/31
        log.info(">>>today: {}",today);

        List<FileVO> fileList = fdao.getFilesByDate(today);
        log.info(">>> 오늘 날짜 파일 개수: {}", fileList.size());

        List<String> currFiles = new ArrayList<String>();
        for (FileVO file : fileList) {
            String fileName = today+File.separator+file.getUuid()+"_"+file.getFileName();
            currFiles.add(DIR+fileName);

            if(file.getFileType().equals("I")){
                String thFileName = today+File.separator+file.getUuid()+"_th_"+file.getFileName();
                currFiles.add(DIR+thFileName);
            }
        }

        log.info(">>> currFiles: {}", currFiles);

        // 경로 기반 저장된 파일 검색
        File dir = Paths.get(DIR+today).toFile();

        // listFiles() : 해당 경로의 모든 파일과 폴더를 File 객체 배열로 반환
        File[] allFileObject = dir.listFiles();

        for(File f : allFileObject){
            if(!currFiles.contains(f.getAbsolutePath())){
                log.info(">>> 삭제 대상 파일: {}", f.getAbsolutePath());
                boolean isDel = f.delete();
                log.info(">>> 파일 삭제 여부: {}", isDel);
            }
        }
        log.info(">>> FileSweeper 스케줄러 종료됨 :  {}", LocalDateTime.now());
    }
}

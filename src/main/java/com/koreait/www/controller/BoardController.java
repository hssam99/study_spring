package com.koreait.www.controller;

import com.koreait.www.domain.BoardFileDTO;
import com.koreait.www.domain.BoardVO;
import com.koreait.www.domain.FileVO;
import com.koreait.www.domain.PagingVO;
import com.koreait.www.handler.FileHandler;
import com.koreait.www.handler.FileRemoveHandler;
import com.koreait.www.handler.PagingHandler;
import com.koreait.www.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board/*")
@Slf4j
@Controller
public class BoardController {

    // 생성자 주입
    private final BoardService bsv;
    private final FileHandler fh;

    // 요청 경로와 jsp의 경로가 일치하면 void로 처리 가능
    @GetMapping("/register")
    public void register() {
        log.info(">>> 게시글 등록 페이지로 이동");
    }

    @PostMapping("/insert")
    public String insert(BoardVO board, @RequestParam("file") MultipartFile[] files){
        List<FileVO> fileList = null;
        if(files != null && files.length > 0 && files[0].getSize() > 0){
            fileList = fh.uploadFile(files);
            log.info(">>> filelist: {}", fileList);
        }

        BoardFileDTO boardFileDTO = new BoardFileDTO(board, fileList);

        int isOk = bsv.insert(boardFileDTO);
        log.info(">>> 게시글 등록: {} ", (isOk > 0) ? "✅성공" : "❌실패");
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model model, PagingVO pvo){
        int totalCount = bsv.getTotalCount(pvo);
        PagingHandler ph = new PagingHandler(totalCount, pvo);
        log.info(">>> paging: ", pvo);
        List<BoardVO> list = bsv.getList(pvo);
        model.addAttribute("list", list);
        model.addAttribute("ph", ph);
        log.info(">>> 리스트 불러오기 > ✅성공");
        return "/board/list";
    }

    @GetMapping({"/detail", "/modify" })
    public void detail(Model model, @RequestParam("bno") Long bno, HttpServletRequest request) {
        if(request.getServletPath().equals("/board/detail")) {
            log.info(">>> 게시글 상세보기 페이지로 이동");
            bsv.upReadCount(bno); // 조회수 증가
        } else if(request.getServletPath().equals("/board/modify")) {
            log.info(">>> 게시글 수정 페이지로 이동");
        }
        BoardFileDTO boardFileDTO = bsv.getDetail(bno);
        model.addAttribute("boardFileDTO", boardFileDTO);
    }

    @PostMapping("/update")
    public String update(BoardVO board, RedirectAttributes re,
                         @RequestParam(value = "file", required = false) MultipartFile[] files ){
        List<FileVO> fileList = null;
        if(files != null && files.length > 0 && files[0].getSize() > 0){
            fileList = fh.uploadFile(files);
            log.info(">>> filelist: {}", fileList);
        }

        BoardFileDTO boardFileDTO = new BoardFileDTO(board, fileList);
        int isOk = bsv.update(boardFileDTO);
        log.info(">>> 게시글 수정:{} ", (isOk > 0) ? "✅성공" : "❌실패");
        re.addAttribute("bno", board.getBno());
        return "redirect:/board/detail";
//        return "redirect:/board/detail?bno=" + board.getBno();
    }

    @ResponseBody
    @DeleteMapping("/deleteFile/{uuid}")
    public String deleteFile(@PathVariable("uuid") String uuid){
        FileRemoveHandler fr = new FileRemoveHandler();
        FileVO fvo = bsv.getFile(uuid);
        fr.deleteFile(fvo);
        int isOk = bsv.deleteFile(uuid);
        log.info(">>> 파일 삭제: {} ", isOk>0 ? "✅성공" : "❌실패");
        return isOk>0 ? "1" : "0";
    }

    @PostMapping("/delete")
    public String delete(Model model, @RequestParam("bno") Long bno){
        
        int isOk = bsv.delete(bno);
        log.info(">>> 게시글 삭제: {} ", (isOk > 0) ? "✅성공" : "❌실패");
        return "redirect:/board/list";
    }
}

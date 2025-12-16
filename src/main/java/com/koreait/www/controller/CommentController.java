package com.koreait.www.controller;

import com.koreait.www.domain.CommentVO;
import com.koreait.www.domain.PagingVO;
import com.koreait.www.handler.PagingHandler;
import com.koreait.www.service.BoardService;
import com.koreait.www.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/comment/*")
@Slf4j
@RestController
public class CommentController {

    private final CommentService csv;

    @PostMapping(value = "/post",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> postComment(@RequestBody CommentVO comment) {
        log.info(">>> 댓글 등록 요청: " + comment);
        int isOk = csv.insert(comment);
        return (isOk>0) ? new ResponseEntity<String>("1", HttpStatus.OK):
                new ResponseEntity<String>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @GetMapping(value = "/list/{bno}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<List<CommentVO>> getCommentList(@PathVariable("bno") Long bno) {
//        List<CommentVO> list = csv.getCmtList(bno);
//        return new ResponseEntity<List<CommentVO>>(list, HttpStatus.OK);
//    }

    @GetMapping(value = "/list/{bno}/{page}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PagingHandler> getList(@PathVariable("bno") Long bno, @PathVariable("page") int page) {
        PagingVO paging = new PagingVO(page, 5);
        PagingHandler ph = csv.getList(bno, paging);
        return new ResponseEntity<PagingHandler>(ph, HttpStatus.OK);
    }


    @ResponseBody
    @PutMapping("/modify")
    public String modify(@RequestBody CommentVO comment) {
        int isOk = csv.update(comment);
        return (isOk > 0) ? "1" : "0";
    }

    @DeleteMapping("/remove/{cno}/{bno}")
    public ResponseEntity<String> remove(
            @PathVariable("cno") Long cno, @PathVariable("bno") Long bno) {
        int isOk = csv.delete(cno, bno);
        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK)
                : new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

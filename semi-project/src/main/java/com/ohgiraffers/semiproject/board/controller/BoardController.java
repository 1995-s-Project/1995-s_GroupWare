package com.ohgiraffers.semiproject.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    // 전사게시판 페이지로 이동
    @GetMapping("/sidemenu/board")
    public String board(){
        return "sidemenu/board/board";
    }
}

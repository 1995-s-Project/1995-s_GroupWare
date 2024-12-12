package com.ohgiraffers.semiproject.board.controller;


import com.ohgiraffers.semiproject.board.model.dto.BoardDTO;
import com.ohgiraffers.semiproject.board.model.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 전사게시판 페이지로 이동
    @GetMapping("/sidemenu/board")
    public String board(Model model){
        List<BoardDTO> boardSelect = boardService.select();

        model.addAttribute("boardSelect", boardSelect);

        return "sidemenu/board/board";
    }

    @GetMapping("sidemenu/board/search")
    public String search(@RequestParam String query, Model model) {
        List<BoardDTO> boardSearch = boardService.search(query);

        model.addAttribute("boardSearch", boardSearch);

        System.out.println("서치 = " + boardSearch);

        return "sidemenu/board/search";
    }


    @GetMapping("/sidemenu/board/regist")
    public void boardRegist(){}

    @PostMapping("/sidemenu/board/regist")
    public String regist(@ModelAttribute BoardDTO board){

        Date now = new Date();

        board.setBoardDate(now);

        boardService.boardRegist(board);

        return "redirect:/sidemenu/board";
    }

    @GetMapping("/sidemenu/board/title")
    public String title(@RequestParam String title, Model model) {
        
        BoardDTO board = boardService.title(title);

        model.addAttribute("board", board);

        System.out.println("결2 = " + board);

        return "sidemenu/board/title";
    }

    @GetMapping("/sidemenu/board/title/delete")
    public String delete(@RequestParam Integer boardCode) {

        boardService.delete(boardCode);

        System.out.println("삭제 = " + boardCode);

        return "redirect:/sidemenu/board";
    }

    @GetMapping("/sidemenu/board/title/update")
    public void update(){}

    @PostMapping("/sidemenu/board/title/update")
    public String boardUpdate(@RequestParam Integer boardCode, Model model) {

        boardService.update(boardCode);

        model.addAttribute("boardDTO", boardCode);

        return "redirect:/sidemenu/board/";
    }

}


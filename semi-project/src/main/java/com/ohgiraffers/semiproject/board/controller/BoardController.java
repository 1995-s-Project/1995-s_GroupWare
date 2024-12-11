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

    @GetMapping("/sidemenu/board/{boardCode}")
    public String title(@PathVariable Integer boardCode, Model model) {

        BoardDTO board = boardService.title(boardCode);
        boardService.viewConut(boardCode);

        model.addAttribute("board", board);

        return "sidemenu/board/title";
    }

    @GetMapping("/sidemenu/board/{boardCode}/delete")
    public String delete(@PathVariable Integer boardCode) {

        boardService.delete(boardCode);

        return "redirect:/sidemenu/board";
    }

    @GetMapping("/sidemenu/board/{boardCode}/update")
    public String update(@PathVariable Integer boardCode, Model model){

        BoardDTO board = boardService.title(boardCode);

        model.addAttribute("board", board);

        return "sidemenu/board/update";
    }

    @PostMapping("/sidemenu/board/update")
    public String boardUpdate(@ModelAttribute BoardDTO boardDTO) {

        boardService.update(boardDTO);

        return "redirect:/sidemenu/board";
    }

    @GetMapping("/sidemenu/title/list")
    public String list(){

        return "sidemenu/board/board";
    }
}


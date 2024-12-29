package com.ohgiraffers.semiproject.board.controller;


import com.ohgiraffers.semiproject.board.model.dto.BoardDTO;
import com.ohgiraffers.semiproject.board.model.dto.BoardEmpDTO;
import com.ohgiraffers.semiproject.board.model.dto.PageDTO;
import com.ohgiraffers.semiproject.board.model.service.BoardService;
import com.ohgiraffers.semiproject.common.UserRole;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class BoardController {
    private final BoardService boardService;
    private final UserInfoService userInfoService;

    public BoardController(BoardService boardService, UserInfoService userInfoService, EmployeeService employeeService) {
        this.boardService = boardService;
        this.userInfoService = userInfoService;
    }

    //  게시판 전체 조회 및 페이징처리
    @GetMapping("/board")
    public String board(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "15") int size) {

        int offset = page * size;

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String job = userInfo.getPosition();

        UserRole userRole = userInfo.getUserRole();

        List<BoardEmpDTO> boardList = boardService.selectAll(offset, size);

        long totalProducts = boardService.getTotalProducts();

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        model.addAttribute("boardList", boardList);

        model.addAttribute("pageInfo", pageInfo);

        model.addAttribute("job", job);

        model.addAttribute("userRole", userRole);

        return "sidemenu/board/board";
    }

    //  검색기능
    @GetMapping("/board/search")
    public String search(@RequestParam String query,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "15") int size,
                         Model model) {
        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String job = userInfo.getPosition();

        UserRole userRole = userInfo.getUserRole();

        int offset = page * size;

        List<BoardEmpDTO> boardSearch = boardService.search(query, offset, size);

        long totalProducts = boardService.getTotalProducts1(query);

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        if (totalPages == 0) {
            totalPages = 1;
        }

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        model.addAttribute("boardSearch", boardSearch);

        model.addAttribute("pageInfo", pageInfo);

        model.addAttribute("query", query);

        model.addAttribute("isEmpty", boardSearch.isEmpty());

        model.addAttribute("job", job);

        model.addAttribute("userRole", userRole);

        return "sidemenu/board/search";
    }

    //  글쓰기로가기
    @GetMapping("/board/regist")
    public String boardRegist(Model model){

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String name = userInfo.getName();

        model.addAttribute("name", name);

        return "sidemenu/board/regist";
    }

    //  글쓰기
    @PostMapping("/board/regist")
    public String regist(@ModelAttribute BoardEmpDTO board){

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        board.setEmpCode(code);

        Date now = new Date();

        board.setBoardDate(now);

        boardService.boardRegist(board);

        return "redirect:/board";
    }

    //  게시판 상세조회
    @GetMapping("/board/{boardCode}")
    public String title(@PathVariable Integer boardCode, Model model) {

        BoardDTO board = boardService.title(boardCode);

        boardService.viewConut(boardCode);

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String job = userInfo.getPosition();

        UserRole userRole = userInfo.getUserRole();

        String userCode = userInfo.getCode();

        model.addAttribute("board", board);

        model.addAttribute("job", job);

        model.addAttribute("userRole", userRole);

        model.addAttribute("userCode", userCode);

        System.out.println("userRole: " + userRole);
        System.out.println("job: " + job);
        System.out.println("userCode: " + userCode);
        System.out.println("board.empCode: " + board.getEmpCode());


        return "sidemenu/board/title";
    }

    //  게시글 삭제
    @GetMapping("/board/{boardCode}/delete")
    public String delete(@PathVariable Integer boardCode) {

        boardService.delete(boardCode);

        return "redirect:/board";
    }

    //  수정창으로가기
    @GetMapping("/board/{boardCode}/update")
    public String update(@PathVariable Integer boardCode, Model model){

        BoardDTO board = boardService.title(boardCode);

        model.addAttribute("board", board);

        return "sidemenu/board/update";
    }

    //  게시판 수정
    @PostMapping("/board/update")
    public String boardUpdate(@ModelAttribute BoardDTO boardDTO) {

        boardService.update(boardDTO);

        return "redirect:/board";
    }

    //  메인으로 가기
    @GetMapping("/board/board")
    public void inventory() {}
}


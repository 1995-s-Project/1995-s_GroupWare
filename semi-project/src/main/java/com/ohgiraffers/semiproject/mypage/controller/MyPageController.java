package com.ohgiraffers.semiproject.mypage.controller;

import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import com.ohgiraffers.semiproject.mypage.model.service.MyPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MyPageController {

    private final UserInfoService userInfoService;
    private final MyPageService myPageService;
    private final EmployeeService employeeService;

    public MyPageController(UserInfoService userInfoService, MyPageService myPageService, EmployeeService employeeService){
        this.userInfoService = userInfoService;
        this.myPageService = myPageService;
        this.employeeService = employeeService;
    }

    // 마이페이지 페이지로 이동
    @GetMapping("/sidemenu/mypage")
    public String mypage() {
        return "sidemenu/mypage/mypage";
    }

    // 마이페이지 - 댓글내역
    @GetMapping("/my-activity/comments")
    public String myComments(Model model) {

        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String code = userInfo.getCode();
        System.out.println("code = " + code);
        List<CommentDTO> comments = employeeService.getUserComment(code);

        System.out.println("comments = " + comments);
        
        model.addAttribute("comments", comments);
        return "sidemenu/mypage/myComments";
    }

    @GetMapping("/my-activity/mails")
    public String myMails() {
        return "sidemenu/mypage/myMails";
    }

    @GetMapping("/my-activity/posts")
    public String myPosts() {
        return "sidemenu/mypage/myPosts";
    }
}

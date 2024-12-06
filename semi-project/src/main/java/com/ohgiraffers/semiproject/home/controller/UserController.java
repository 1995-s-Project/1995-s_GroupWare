package com.ohgiraffers.semiproject.home.controller;

import com.ohgiraffers.semiproject.home.auth.model.service.MemberService;
import com.ohgiraffers.semiproject.home.model.dto.SignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    // 시간상 필드주입
    @Autowired
    private MemberService memberService;

    @GetMapping("/user/signup")
    public void signupPage(){}

    @PostMapping("/user/signup")
    public ModelAndView signup(@ModelAttribute SignupDTO signupDTO, ModelAndView mv){

        System.out.println(signupDTO);

        Integer result = memberService.regist(signupDTO);

        String message = null;

        /* comment. controller 의 역할은 어떠한 view 를 보여줄 지 선택하는 것 */
        if(result == null){
            message = "중복 된 회원이 존재합니다.";
        } else if (result == 0) {
            message = "서버 내부에서 오류가 발생했습니다.";
            mv.setViewName("home");
        } else if(result >= 1){
            message = "회원 가입이 완료되었습니다.";
            mv.setViewName("home");
        }

        mv.addObject("message", message);

        return mv;
    }
}

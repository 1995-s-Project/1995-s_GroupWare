package com.ohgiraffers.semiproject.adoption.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdoptionController {

    // 입양 페이지로 이동
    @GetMapping("/sidemenu/adoption")
    public String adoption(){
        return "sidemenu/adoption/adoption";
    }
}

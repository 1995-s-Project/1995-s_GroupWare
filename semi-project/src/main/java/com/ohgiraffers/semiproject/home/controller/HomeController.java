package com.ohgiraffers.semiproject.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("home/no-search")
    public String noSearch(){
        return "/home/no-search";
    }

    @GetMapping("home/pass-search")
    public String passSearch(){
        return "/home/pass-search";
    }
}

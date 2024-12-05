package com.ohgiraffers.semiproject.animals.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnimalsController {

    // 동물 페이지로 이동
    @GetMapping("/sidemenu/animals")
    public String animals(){
        return "sidemenu/animals/animals";
    }

    // 입양완료 페이지로 이동
    @GetMapping("/sidemenu/adoptionComplete")
    public String adoptionComplete(){
        return "sidemenu/animals/adoptionComplete";
    }

    // 재고관리 페이지로 이동
    @GetMapping("/sidemenu/stock")
    public String stock(){
        return "sidemenu/animals/stock";
    }
}

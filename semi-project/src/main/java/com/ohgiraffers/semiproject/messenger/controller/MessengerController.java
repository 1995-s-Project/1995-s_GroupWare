package com.ohgiraffers.semiproject.messenger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessengerController {

    // 메신저(채팅) 페이지로 이동
    @GetMapping("/sidemenu/messenger")
    public String messenger(){
        return "sidemenu/messenger/messenger";
    }
}

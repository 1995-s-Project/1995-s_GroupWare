package com.ohgiraffers.semiproject.mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MailController {

    // 이메일 페이지로 이동
    @GetMapping("/sidemenu/mail")
    public String mail() {
        return "sidemenu/mail/mail";
    }
}

package com.ohgiraffers.semiproject.mail.controller;

import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import com.ohgiraffers.semiproject.mail.model.service.MailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    // 이메일 페이지로 이동
    @GetMapping("/sidemenu/mail")
    public String mail() {
        return "sidemenu/mail/mail";
    }

    @GetMapping("mail")
    public String mailAllSelect(Model model) {
        List<MailDTO> mail = mailService.mailAllSelect();

        model.addAttribute("mail", mail);

        return "sidemenu/mail/mail";
    }
}

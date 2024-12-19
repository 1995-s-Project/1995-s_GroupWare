package com.ohgiraffers.semiproject.mail.controller;

import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import com.ohgiraffers.semiproject.mail.model.service.MailService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class MailController {

    private final MailService mailService;
    private final EmployeeService employeeService;
    private final UserInfoService userInfoService;

    public MailController(MailService mailService, EmployeeService employeeService, UserInfoService userInfoService) {
        this.mailService = mailService;
        this.employeeService = employeeService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/sidemenu/mail")
    public String mailAllSelect(Model model) {


        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        List<MailDTO> mail = mailService.mailAllSelect(code);

        model.addAttribute("mailList", mail);

        return "sidemenu/mail/mail";

    }

    @GetMapping("/mail/sent")
    public String mailSentSelect(Model model) {

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        List<MailDTO> mail = mailService.mailSentSelect(code);

        model.addAttribute("mailList", mail);

        return "sidemenu/mail/mailSent";
    }


    @GetMapping("/mail/details/{emailCode}")
    public String mailDetail(@PathVariable Integer emailCode, Model model) {

        MailDTO mail = mailService.mailDetail(emailCode);

        model.addAttribute("mail", mail);

        return "sidemenu/mail/mailDetail";
    }

    // 직원정보 보내기
    @GetMapping("api/employee")
    public ResponseEntity<List<EmployeeJoinListDTO>> employee() {

        List<EmployeeJoinListDTO> employeeList = employeeService.empAllSelect();
        for (EmployeeJoinListDTO list : employeeList) {
            System.out.println("list = " + list);
        }
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/sidemenu/mail/regist")
    public String registMail(@RequestParam("recipientId") String recipientId, @RequestParam("recipientName") String recipientName, @ModelAttribute MailDTO mailDTO) {

        // recipientId 값을 mailDTO에 설정
        mailDTO.setRecipientId(recipientId);
        mailDTO.setRecipientName(recipientName);
        System.out.println("쪽지 = " + mailDTO);

        // 나머지 로직
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String code = userInfo.getCode(); // 로그인된 사번
        Date now = new Date();

        mailDTO.setSendDate(now);

        mailDTO.setSenderId(code); // 로그인한 사용자 사번으로 발신자 설정

        mailService.registMail(mailDTO);

        System.out.println("쪽지쓰기 = " + mailDTO);

        return "redirect:/sidemenu/mail"; // 메인 페이지로 리다이렉트
    }

//    @GetMapping("/mail/{emailCode}/trash/")
//    public String mailTrash(@PathVariable Integer emailCode, Model model) {
//
//        mailService.mailTrash(emailCode);
//
//        return "";
//    }
}

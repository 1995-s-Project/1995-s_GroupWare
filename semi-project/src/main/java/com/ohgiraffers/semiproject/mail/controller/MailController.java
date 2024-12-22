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

import java.util.*;
import java.util.stream.Collectors;

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


    @GetMapping("api/employee")
    public ResponseEntity<List<EmployeeJoinListDTO>> employee() {

        List<EmployeeJoinListDTO> employeeList = employeeService.empAllSelect();
        for (EmployeeJoinListDTO list : employeeList) {

        }
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/sidemenu/mail/regist")
    public String registMail(@RequestParam("recipientId") String recipientId, @RequestParam("recipientName") String recipientName, @ModelAttribute MailDTO mailDTO) {


        mailDTO.setRecipientId(recipientId);

        mailDTO.setRecipientName(recipientName);

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        Date now = new Date();

        mailDTO.setSendDate(now);

        mailDTO.setSenderId(code);

        mailService.registMail(mailDTO);

        return "redirect:/sidemenu/mail";
    }

    @GetMapping("/mail/important")
    public String mailImportant(Model model){

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        List<MailDTO> important = mailService.mailFolderImportant(code);

        model.addAttribute("important", important);

        return "sidemenu/mail/important";
    }

    @GetMapping("/mail/trash")
    public String mailTrash(Model model) {

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        List<MailDTO> trash = mailService.mailFolderTrash(code);

        System.out.println("trash = " + trash);

        model.addAttribute("trash", trash);

        return "sidemenu/mail/trash";
    }

    @GetMapping("/mail/archived")
    public String mailArchived(Model model) {

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        List<MailDTO> archived = mailService.mailFolderArchived(code);

        model.addAttribute("archived", archived);

        return "sidemenu/mail/archived";
    }

    @PostMapping("/mail/move")
    @ResponseBody
    public ResponseEntity<String> moveMails(@RequestBody MailDTO mailDTO) {
        if (mailDTO.getEmailCode() == null || mailDTO.getEmailCode().isEmpty()) {
            return ResponseEntity.badRequest().body("메일 ID가 비어 있습니다.");
        }
        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        String[] emailCodes = mailDTO.getEmailCode().split(",");

        List<Integer> mail = Arrays.stream(emailCodes)

                .map(Integer::valueOf)

                .collect(Collectors.toList());

        mailService.moveMails(mail, mailDTO.getFolder(), code);

        return ResponseEntity.ok("메일 이동 성공");
    }
}


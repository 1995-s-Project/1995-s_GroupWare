package com.ohgiraffers.semiproject.mail.controller;

import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import com.ohgiraffers.semiproject.mail.model.service.MailService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import org.springframework.http.HttpStatus;
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

        model.addAttribute("mail", mail);
        model.addAttribute("isEmpty", mail.isEmpty());

        return "sidemenu/mail/mail";

    }

    @GetMapping("/mail/sent")
    public String mailSentSelect(Model model) {

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        List<MailDTO> mail = mailService.mailSentSelect(code);

        model.addAttribute("mail", mail);
        model.addAttribute("isEmpty", mail.isEmpty());

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
    public String registMail(
            @RequestParam("recipientId") String recipientId,
            @RequestParam("recipientName") String recipientName,
            @ModelAttribute MailDTO mailDTO) {

        // 수신자 ID와 이름을 쉼표로 구분하여 List로 변환
        List<String> recipientIds = Arrays.asList(recipientId.split(","));
        List<String> recipientNames = Arrays.asList(recipientName.split(","));

        // 발신자 정보 설정
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String code = userInfo.getCode();
        Date now = new Date();
        mailDTO.setSendDate(now);
        mailDTO.setSenderId(code);

        // 각 수신자에 대해 메일 등록 (개별 메일 등록은 서비스에서 처리됨)
        mailService.registMail(mailDTO, recipientIds, recipientNames); // 메일 등록

        return "redirect:/sidemenu/mail";  // 메일 목록 화면으로 리다이렉트
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

        model.addAttribute("isEmpty", trash.isEmpty());

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

    @PostMapping("/mail/delete")
    @ResponseBody
    public ResponseEntity<List<MailDTO>> deleteMails(@RequestBody MailDTO mailDTO) {
        try {

            if (mailDTO.getEmailCode() == null || mailDTO.getEmailCode().isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            UserInfoResponse userInfo = userInfoService.getUserInfo();

            String recipientId = userInfo.getCode();  // 로그인한 사용자 ID

            String[] emailCodes = mailDTO.getEmailCode().split(",");

            List<Integer> mail = Arrays.stream(emailCodes)
                    .map(Integer::valueOf)  // String -> Integer 변환
                    .collect(Collectors.toList());

            mailService.deleteMails(mail, recipientId);

            List<MailDTO> updatedTrash = mailService.mailFolderTrash(recipientId);

            return ResponseEntity.ok(updatedTrash);
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/mail/sent")
    @ResponseBody
    public ResponseEntity<String> sentMoveMails(@RequestBody MailDTO mailDTO) {
        if (mailDTO.getEmailCode() == null || mailDTO.getEmailCode().isEmpty()) {
            return ResponseEntity.badRequest().body("메일 ID가 비어 있습니다.");
        }
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String code = userInfo.getCode();
        String[] emailCodes = mailDTO.getEmailCode().split(",");
        List<Integer> mail = Arrays.stream(emailCodes)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
        mailService.sentMoveMails(mail, mailDTO.getFolder(), code);

        return ResponseEntity.ok("메일 이동 성공");
    }

}

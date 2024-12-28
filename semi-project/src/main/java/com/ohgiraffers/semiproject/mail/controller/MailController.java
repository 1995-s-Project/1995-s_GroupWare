package com.ohgiraffers.semiproject.mail.controller;

import com.ohgiraffers.semiproject.board.model.dto.PageDTO;
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

    @GetMapping("/mail")
    public String mailAllSelect(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size) {

        int offset = Math.max(page * size, 0);

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        long totalProducts = mailService.inboxTotalProducts(code);

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        if (totalProducts == 0) {
            totalPages = 1;
        }

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        List<MailDTO> mail = mailService.mailAllSelect(code, offset, size);

        model.addAttribute("mail", mail);
        model.addAttribute("isEmpty", mail.isEmpty());
        model.addAttribute("pageInfo", pageInfo);

        return "sidemenu/mail/mail";
    }

    @GetMapping("/mail/sent")
    public String mailSentSelect(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "15") int size) {
        int offset = Math.max(page * size, 0); // 음수 방지

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        long totalProducts = mailService.sentTotalProducts(code);

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        if (totalProducts == 0) {
            totalPages = 1;
        }

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        List<MailDTO> mail = mailService.mailSentSelect(code, offset, size);

        model.addAttribute("mail", mail);
        model.addAttribute("isEmpty", mail.isEmpty());
        model.addAttribute("pageInfo", pageInfo);

        return "sidemenu/mail/mailSent";
    }

    @GetMapping("/mail/details/{emailCode}")
    public String mailDetail(@PathVariable Integer emailCode, Model model) {

        MailDTO mail = mailService.mailDetail(emailCode);

        model.addAttribute("mail", mail);

        return "sidemenu/mail/mailDetail";
    }

    @GetMapping("/mail/employees")
    public ResponseEntity<List<EmployeeJoinListDTO>> employee() {

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        List<EmployeeJoinListDTO> employeeList = employeeService.mailAllSelect(code);

        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/sidemenu/mail/regist")
    public String registMail(
            @RequestParam("recipientId") String recipientId,
            @RequestParam("recipientName") String recipientName,
            @ModelAttribute MailDTO mailDTO) {

        List<String> recipientIds = Arrays.asList(recipientId.split(","));

        List<String> recipientNames = Arrays.asList(recipientName.split(","));

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        String senderName = userInfo.getName();

        Date now = new Date();

        mailDTO.setSendDate(now);

        mailDTO.setSenderId(code);

        mailDTO.setSenderName(senderName);

        mailService.inboxRegistMail(mailDTO, recipientIds, recipientNames);

        mailService.sentRegistMail(mailDTO, recipientIds, recipientNames);

        return "redirect:/mail";
    }

    @GetMapping("/mail/important")
    public String mailImportant(Model model,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "15") int size){

        int offset = Math.max(page * size, 0);

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        long totalProducts = mailService.importantTotalProducts(code);

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        if (totalProducts == 0) {
            totalPages = 1;
        }

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        List<MailDTO> important = mailService.mailFolderImportant(code, offset, size);

        model.addAttribute("important", important);
        model.addAttribute("pageInfo", pageInfo);

        return "sidemenu/mail/important";
    }

    @GetMapping("/mail/trash")
    public String mailTrash(Model model,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "15") int size) {

        int offset = Math.max(page * size, 0);

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        long totalProducts = mailService.trashTotalProducts(code);

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        if (totalProducts == 0) {
            totalPages = 1;
        }

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        List<MailDTO> trash = mailService.mailFolderTrash(code, offset, size);

        model.addAttribute("trash", trash);
        model.addAttribute("isEmpty", trash.isEmpty());
        model.addAttribute("pageInfo", pageInfo);

        return "sidemenu/mail/trash";
    }

    @GetMapping("/mail/archived")
    public String mailArchived(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "15") int size) {

        int offset = Math.max(page * size, 0);

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        long totalProducts = mailService.archivedTotalProducts(code);

        int totalPages = (int) Math.ceil((double) totalProducts / size);

        if (totalProducts == 0) {
            totalPages = 1;
        }

        PageDTO pageInfo = new PageDTO(page, size, totalPages);

        List<MailDTO> archived = mailService.mailFolderArchived(code, offset, size);

        model.addAttribute("archived", archived);
        model.addAttribute("pageInfo", pageInfo);

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

        mailService.moveMails(mail, mailDTO.getRecipientFolder(), code);

        return ResponseEntity.ok("메일 이동 성공");
    }

    @PostMapping("/mail/sent")
    @ResponseBody
    public ResponseEntity<String> sentMoveMails(@RequestBody MailDTO mailDTO, Model model) {

        if (mailDTO.getEmailCode() == null || mailDTO.getEmailCode().isEmpty()) {
            return ResponseEntity.badRequest().body("메일 ID가 비어 있습니다.");
        }

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        String[] emailCodes = mailDTO.getEmailCode().split(",");

        List<Integer> mail = Arrays.stream(emailCodes)
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        mailService.sentMoveMails(mail, mailDTO.getSenderFolder(), code);

        model.addAttribute("loggedInUserCode", userInfo.getCode());

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

            String recipientId = userInfo.getCode();

            String[] emailCodes = mailDTO.getEmailCode().split(",");

            List<Integer> mail = Arrays.stream(emailCodes)
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            mailService.inboxDeleteMails(mail, recipientId);

            mailService.sentDeleteMails(mail, recipientId);

            List<MailDTO> updatedTrash = mailService.mailFolderTrash(recipientId, 0, 15);

            return ResponseEntity.ok(updatedTrash);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/mail/important")
    @ResponseBody
    public ResponseEntity<String> importantMails(@RequestBody MailDTO mailDTO) {

        if (mailDTO.getEmailCode() == null || mailDTO.getEmailCode().isEmpty()) {
            return ResponseEntity.badRequest().body("메일 ID가 비어 있습니다.");
        }

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        String[] emailCodes = mailDTO.getEmailCode().split(",");

        List<Integer> mail = Arrays.stream(emailCodes)
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        mailService.importantMails(mail, mailDTO.getRecipientFolder(), code);

        return ResponseEntity.ok("메일 이동 성공");
    }

    @PostMapping("/mail/archived")
    @ResponseBody
    public ResponseEntity<String> archivedMails(@RequestBody MailDTO mailDTO) {

        if (mailDTO.getEmailCode() == null || mailDTO.getEmailCode().isEmpty()) {
            return ResponseEntity.badRequest().body("메일 ID가 비어 있습니다.");
        }

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        String[] emailCodes = mailDTO.getEmailCode().split(",");

        List<Integer> mail = Arrays.stream(emailCodes)
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        mailService.archivedMails(mail, mailDTO.getRecipientFolder(), code);

        return ResponseEntity.ok("메일 이동 성공");
    }

    @GetMapping("/sidemenu/mail/mail")
    public void mail(){}
}

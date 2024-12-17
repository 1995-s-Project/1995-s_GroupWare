package com.ohgiraffers.semiproject.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerApprovalController {

    @GetMapping("/approval/vacation-form")
    public String vacationForm(){
        return  "sidemenu/approval/vacation-form";
    }
}

package com.ohgiraffers.semiproject.schedule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class ScheduleController {

    // 근태관리 페이지 이동
    @GetMapping("/sidemenu/schedule")
    public String schedule() {
        return "sidemenu/schedule/schedule";
    }
}
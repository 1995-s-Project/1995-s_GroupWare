package com.ohgiraffers.semiproject.schedule.controller;

import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import com.ohgiraffers.semiproject.schedule.model.dto.AttendanceRequest;
import com.ohgiraffers.semiproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.semiproject.schedule.model.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
public class AttendanceAPIController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private final EmployeeService employeeService;

    public AttendanceAPIController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/api/employees")
    public ResponseEntity<List<EmployeeJoinListDTO>> employeeList(){

        List<EmployeeJoinListDTO> employeeList = employeeService.empAllSelect();

        return ResponseEntity.ok(employeeList);
    }


    @PostMapping("/api/get-attendance")
    public ResponseEntity<?> getAttendance(@RequestBody AttendanceRequest request) {
        // request에서 date, time, type을 가져옴
        String date = request.getDate();
        String type = request.getType();

        // UserInfoService를 통해 현재 로그인한 사용자의 정보를 가져옴
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String userCode = userInfo.getCode(); // 사번 가져오기

        // DB에서 해당 정보를 조회하는 로직
        ScheduleDTO attendanceData;

        if ("출근".equals(type)) {
            // 출근 수정에 대한 데이터 조회
            attendanceData = scheduleService.getAttendanceByWorkStartTime(userCode, date);
            LocalDateTime workStartTime = attendanceData.getWorkStartTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedWorkStartTime = workStartTime.format(formatter);

            // 클라이언트에 JSON 형식으로 응답
            return ResponseEntity.ok(Collections.singletonMap("attendanceTime", formattedWorkStartTime));

        } else if ("퇴근".equals(type)) {
            // 퇴근 수정에 대한 데이터 조회
            attendanceData = scheduleService.getAttendanceByWorkEndTime(userCode, date);
            LocalDateTime workEndTime = attendanceData.getWorkEndTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedWorkEndTime = workEndTime.format(formatter);

            // 클라이언트에 JSON 형식으로 응답
            return ResponseEntity.ok(Collections.singletonMap("attendanceTime", formattedWorkEndTime));
        } else {
            return ResponseEntity.badRequest().body("유효하지 않은 타입입니다.");
        }
    }
}

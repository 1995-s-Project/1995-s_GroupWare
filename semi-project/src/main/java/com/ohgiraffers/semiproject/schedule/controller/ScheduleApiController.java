package com.ohgiraffers.semiproject.schedule.controller;

import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import com.ohgiraffers.semiproject.schedule.model.dto.CheckInRequestDTO;
import com.ohgiraffers.semiproject.schedule.model.dto.CheckOutRequestDTO;
import com.ohgiraffers.semiproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.semiproject.schedule.model.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sidemenu/schedule/*")
public class ScheduleApiController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("checkin")
    public ResponseEntity<String> checkIn(@RequestBody CheckInRequestDTO checkInRequest) {
        // 요청에서 받은 날짜와 시간을 처리
        String date = checkInRequest.getDate(); // 예: "2024-12-11"
        String startTime = checkInRequest.getStartTime(); // 예: "09:00:00"

        // 날짜를 LocalDate로 변환
        LocalDate workDate;
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            workDate = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("날짜 형식이 잘못되었습니다.");
        }

        // 시간을 LocalTime으로 변환
        LocalTime workStartTime;
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            workStartTime = LocalTime.parse(startTime, timeFormatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("시간 형식이 잘못되었습니다.");
        }

        // LocalDate와 LocalTime을 결합하여 LocalDateTime으로 변환
        LocalDateTime workStartDateTime = LocalDateTime.of(workDate, workStartTime);

        // UserInfoService를 통해 현재 로그인한 사용자의 정보를 가져옴
        UserInfoResponse userInfo = userInfoService.getUserInfo();

        if (userInfo != null) {
            String userCode = userInfo.getCode(); // 사번 가져오기

            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setEmpCode(userCode);
            scheduleDTO.setWorkDate(Date.from(workStartDateTime.atZone(ZoneId.systemDefault()).toInstant())); // Date 타입으로 설정
            scheduleDTO.setWorkStartTime(workStartDateTime); // LocalDateTime으로 설정
            scheduleDTO.setWorkType("출근"); // work_type을 "출근"으로 설정

            // 출근 시간을 데이터베이스에 저장
            scheduleService.insertWorkStartTime(scheduleDTO);

            // 성공적으로 처리되었음을 응답
            return ResponseEntity.ok("출근 처리 완료: " + date + " " + startTime + " (사번: " + userCode + ")");
        } else {
            return ResponseEntity.status(401).body("사용자 정보가 없습니다."); // 인증되지 않은 경우
        }
    }

    @PostMapping("checkout")
    public ResponseEntity<String> checkOut(@RequestBody CheckOutRequestDTO checkOutRequest) {
        // 요청에서 받은 날짜와 시간을 처리
        String date = checkOutRequest.getDate(); // 예: "2024-12-11"
        String endTime = checkOutRequest.getEndTime(); // 예: "09:00:00"

        // 날짜를 LocalDate로 변환
        LocalDate workDate;
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            workDate = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("날짜 형식이 잘못되었습니다.");
        }

        // 시간을 LocalTime으로 변환
        LocalTime workEndTime;
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            workEndTime = LocalTime.parse(endTime, timeFormatter);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body("시간 형식이 잘못되었습니다.");
        }

        // LocalDate와 LocalTime을 결합하여 LocalDateTime으로 변환
        LocalDateTime workEndDateTime = LocalDateTime.of(workDate, workEndTime);

        // 총 근무 시간 계산
        int totalHours = checkOutRequest.getTotalHours();
        int totalMinutes = checkOutRequest.getTotalMinutes();
        int totalSeconds = checkOutRequest.getTotalSeconds();

        // 총 근무 시간을 분으로 변환 (초를 분으로 변환)
        int totalWorkedMinutes = totalHours * 60 + totalMinutes + totalSeconds / 60;

        // 8시간 기준으로 정상 퇴근, 연장 구분
        String status;
        if (totalWorkedMinutes >= 510) { // 8시간 30분 = 510분
            status = "연장"; // 8시간 30분 이상 근무 시 연장으로 설정
        } else if (totalWorkedMinutes >= 480) { // 8시간 = 480분
            status = "정상"; // 8시간 근무 시 정상으로 설정
        } else if (totalWorkedMinutes > 0) {
            status = "조퇴"; // 8시간 미만 근무 시 조퇴로 설정
        } else {
            status = "결근"; // 근무 시간이 0인 경우
        }

        // UserInfoService를 통해 현재 로그인한 사용자의 정보를 가져옴
        UserInfoResponse userInfo = userInfoService.getUserInfo();

        if (userInfo != null) {
            String userCode = userInfo.getCode(); // 사번 가져오기

            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setEmpCode(userCode);

            // workEndDateTime을 Date로 변환
            Date workEndDate = Date.from(workEndDateTime.atZone(ZoneId.systemDefault()).toInstant());

            // workEndDate를 ScheduleDTO에 설정
            scheduleDTO.setWorkDate(workEndDate); // Date 타입으로 설정
            scheduleDTO.setWorkEndTime(workEndDateTime); // LocalDateTime으로 설정
            scheduleDTO.setWorkType(status);

            // 퇴근 시간을 데이터베이스에 저장
            scheduleService.insertWorkEndTime(scheduleDTO);

            // 성공적으로 처리되었음을 응답
            return ResponseEntity.ok(status + " 처리 완료: " + date + " " + endTime + " (사번: " + userCode + ")");
        } else {
            return ResponseEntity.status(401).body("사용자 정보가 없습니다."); // 인증되지 않은 경우
        }
    }

    @GetMapping("getSchedule")
    public ResponseEntity<List<ScheduleDTO>> getSchedule() {

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        if (userInfo != null) {
            String userCode = userInfo.getCode();
            List<ScheduleDTO> schedules = scheduleService.getSchedulesByUserCode(userCode);

            return ResponseEntity.ok(schedules);
        } else {
            return ResponseEntity.status(401).body(null); // 인증되지 않은 경우
        }
    }

}

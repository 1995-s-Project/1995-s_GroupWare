package com.ohgiraffers.semiproject.manager.controller;

import com.ohgiraffers.semiproject.approval.model.service.ApprovalService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import com.ohgiraffers.semiproject.manager.model.dto.VacPaymentDTO;
import com.ohgiraffers.semiproject.manager.model.service.ManagerApprovalService;
import com.ohgiraffers.semiproject.schedule.model.dto.VacationDTO;
import com.ohgiraffers.semiproject.schedule.model.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class ManagerApprovalController {

    private final ManagerApprovalService managerApprovalService;
    private final ApprovalService approvalService;
    private final UserInfoService userInfoService;
    private final ScheduleService scheduleService;

    @Autowired
    public ManagerApprovalController(ManagerApprovalService managerApprovalService,
                                     ApprovalService approvalService, UserInfoService userInfoService,
                                     ScheduleService scheduleService){
        this.managerApprovalService = managerApprovalService;
        this.approvalService = approvalService;
        this.userInfoService = userInfoService;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/approval/vacation-form")
    public String vacationForm(){
        return  "sidemenu/approval/vacation-form";
    }

    @PostMapping("/vacationFormRequest")
    public String vacationFormRequest(@ModelAttribute VacPaymentDTO vacPaymentDTO, Model model){

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        vacPaymentDTO.setEmpCode(code);
        vacPaymentDTO.setOwnerCode("025"); // 대표 사번
        vacPaymentDTO.setProgressCode("SU1");

        approvalService.insertVacForm(vacPaymentDTO);

        return "/sidemenu/manager/approvalBox";
    }

    @GetMapping("/readVacRequest")
    public String approvalRequest(@RequestParam("no") String documentNo, Model model) {

        VacPaymentDTO response = managerApprovalService.getVacRequest(documentNo);

        VacPaymentDTO updatedProcess = managerApprovalService.getVacationDetails(documentNo);

        UserInfoResponse userInfo = userInfoService.getUserInfo();

        String code = userInfo.getCode();

        // 관리자 or 대표 승인 값 가져오기
        model.addAttribute("adminAccept", updatedProcess.getManagerAccept());
        model.addAttribute("ownerAccept", updatedProcess.getPresidentAccept());

        // no 값 넘기기
        model.addAttribute("no", documentNo);

        // 포맷팅된 날짜를 모델에 추가
        model.addAttribute("formattedDeadLineDate", response.getFormattedDeadLineDate());
        model.addAttribute("formattedVacStartDate", response.getFormattedVacStartDate());
        model.addAttribute("formattedVacEndDate", response.getFormattedVacEndDate());

        model.addAttribute("vacRequestInfo", response);

        // 현재 로그인한 사번 코드
        model.addAttribute("currentEmpCode", code);

        // 관리자 사번 코드
        model.addAttribute("adminCode", response.getAdminCode());
        model.addAttribute("ownerCode", response.getOwnerCode());

        return "sidemenu/approval/readVacRequest";
    }

    @PostMapping("/vacationProcess")
    public String vacationProcess(@ModelAttribute VacPaymentDTO process) {

        // managerAccept 값에 따라 progressCode 설정
        if (process.getManagerAccept() != null) {
            if (process.getManagerAccept().equals("/img/icon/Stamp.png")) {

                process.setProgressCode("SU2"); // 진행중으로 변경
            } else if (process.getManagerAccept().equals("반려")) {
                process.setProgressCode("SU4"); // 반려로 설정
            }
        }

        // presidenAccept 값에 따라 progressCode 설정
        if (process.getPresidentAccept() != null) {
            if (process.getPresidentAccept().equals("/img/icon/Stamp.png")) {
                process.setManagerAccept("/img/icon/Stamp.png");
                process.setProgressCode("SU3"); // SU3으로 설정
            } else if (process.getPresidentAccept().equals("반려")) {
                process.setManagerAccept("/img/icon/Stamp.png");
                process.setProgressCode("SU4"); // 반려로 설정
            }
        }

        System.out.println("process = " + process);

        // 승인 처리
        managerApprovalService.vacationProcess(process);

        // 기안자 신청서 시작날짜 마지막날짜 가져오기
        VacPaymentDTO response = managerApprovalService.getDate(process.getDocumentNo());

        // 기안자 휴가 정보 가져오기
        VacationDTO vacationDTO = scheduleService.getVacation(response.getEmpCode());

        if ((response.getType().equals("연차")) &&
                (response.getManagerAccept().equals("/img/icon/Stamp.png")) &&
                (response.getPresidentAccept().equals("/img/icon/Stamp.png"))) {

            String code = response.getEmpCode();

            int annualLeave = vacationDTO.getAnnualLeave();

            // 시작일과 종료일을 Date 객체로 변환
            Date vacStartDate = response.getVacStartDate(); // 예: Thu Dec 26 00:00:00 KST 2024
            Date vacEndDate = response.getVacEndDate(); // 예: Tue Dec 31 00:00:00 KST 2024

            // 날짜 포맷 설정
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();

            startCal.setTime(vacStartDate);
            endCal.setTime(vacEndDate);

            // 시작일과 종료일의 차이를 계산 (종료일 포함하지 않음)
            int daysBetween = (int) ((endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24));

            // 연차 차감
            annualLeave -= daysBetween + 1; // 시작일 포함

            System.out.println("연차 차감된 결과값 : " + annualLeave);

            // 업데이트된 연차 수를 저장
            vacationDTO.setAnnualLeave(annualLeave);
            scheduleService.updateVacation(code, vacationDTO);

        } else if ((response.getType().equals("휴가")) &&
                (response.getManagerAccept().equals("/img/icon/Stamp.png")) &&
                (response.getPresidentAccept().equals("/img/icon/Stamp.png"))) {

            String code = response.getEmpCode();

            int vacation = vacationDTO.getAnnualLeave();

            // 시작일과 종료일을 Date 객체로 변환
            Date vacStartDate = response.getVacStartDate(); // 예: Thu Dec 26 00:00:00 KST 2024
            Date vacEndDate = response.getVacEndDate(); // 예: Tue Dec 31 00:00:00 KST 2024

            // 날짜 포맷 설정
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar startCal = Calendar.getInstance();
            Calendar endCal = Calendar.getInstance();

            startCal.setTime(vacStartDate);
            endCal.setTime(vacEndDate);

            // 시작일과 종료일의 차이를 계산 (종료일 포함하지 않음)
            int daysBetween = (int) ((endCal.getTimeInMillis() - startCal.getTimeInMillis()) / (1000 * 60 * 60 * 24));

            // 연차 차감
            vacation -= daysBetween + 1; // 시작일 포함

            System.out.println("연차 차감된 결과값 : " + vacation);

            // 업데이트된 연차 수를 저장
            vacationDTO.setVacation(vacation);
            scheduleService.updateVacation(code, vacationDTO);
        }

        return "redirect:/readVacRequest?no=" + process.getDocumentNo();
        }

}

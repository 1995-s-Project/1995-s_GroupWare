package com.ohgiraffers.semiproject.mail.model.dto;

import lombok.*;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MailDTO {

    private String emailCode; // 메일코드
    private String title; // 쪽지제목
    private String file; // 첨부파일
    private String content; // 내용
    private Date sendDate; // 전송일자
    private Date deleteDate; // 삭제일자
    private String receptionType; // 수신종류
    private String receptionStatus; // 수신상태
    private String senderId; // 발신자 사번
    private String recipientId; // 다수의 recipientId를 저장할 리스트
    private String recipientName; // 다수의 recipientName을 저장할 리스트
    private EmployeeDTO employeeDTO;
    private JobDTO jobDTO;
    private DeptDTO deptDTO;
    private String folder;
    private String getEmail;
}

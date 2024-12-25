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

    private String emailCode;
    private String title;
    private String file;
    private String content;
    private Date sendDate;
    private Date deleteDate;
    private String receptionType;
    private String receptionStatus;
    private String senderId;
    private String recipientId;
    private String recipientName;
    private EmployeeDTO employeeDTO;
    private JobDTO jobDTO;
    private DeptDTO deptDTO;
    private String recipientFolder;
    private String senderFolder;
}

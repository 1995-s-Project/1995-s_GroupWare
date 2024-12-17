package com.ohgiraffers.semiproject.mail.model.dto;

import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MailDTO {

    private String emailCode;
    private String importantEmail;
    private String emailTitle;
    private String emailFile;
    private String emailContent;
    private Date sendDate;
    private Date deleteDate;
    private String address;
    private String mailboxNo;
    private String receptionType;
    private String receptionStatus;
    private String senderId;
    private String recipientId;
    private EmployeeDTO employeeDTO;

}

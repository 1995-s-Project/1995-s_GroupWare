package com.ohgiraffers.semiproject.manager.model.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VacPaymentDTO {

    private int documentNo;
    private String empCode;
    private Date deadline_date;
    private String type;
    private Date vacStartDate;
    private Date vacEndDate;
    private String vacReason;
    private String managerAccept;
    private String presidentAccept;
    private String progressCode;
    private String adminCode;
    private String ownerCode;
}

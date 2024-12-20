package com.ohgiraffers.semiproject.manager.model.dto;

import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RetirementDTO {

    private int documentNo;
    private String empCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deadLineDate;
    private String type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date retireDate;
    private String retireReason;
    private String managerAccept;
    private String presidentAccept;
    private String progressCode;
    private String adminCode;
    private String adminName;
    private String ownerCode;
    private EmployeeJoinListDTO userInfo;

    public String getFormattedDeadLineDate() {
        return formatDate(deadLineDate);
    }

    public String getFormattedCacStartDate() {
        return formatDate(retireDate);
    }

    private String formatDate(Date date) {
        if (date != null) {
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return null; // 또는 적절한 기본값
    }
}

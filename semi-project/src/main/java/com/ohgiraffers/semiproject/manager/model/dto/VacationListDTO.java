package com.ohgiraffers.semiproject.manager.model.dto;

import com.ohgiraffers.semiproject.employee.model.dto.DeptDTO;
import com.ohgiraffers.semiproject.employee.model.dto.JobDTO;
import com.ohgiraffers.semiproject.schedule.model.dto.VacationDTO;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VacationListDTO {
    private String empCode;
    private String empPass;
    private String empName;
    private String empEmail;
    private String empPhone;
    private Date hireDate;
    private Date entDate;
    private String empGender;
    private String empAddress;
    private String jobCode;
    private String deptCode;
    private String authority;
    private String profile_image;
    private DeptDTO deptDTO;
    private JobDTO jobDTO;
    private VacationDTO vacationDTO;
}

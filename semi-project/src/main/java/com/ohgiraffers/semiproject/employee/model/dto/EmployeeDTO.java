package com.ohgiraffers.semiproject.employee.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeDTO {
    private String code;
    private String name;
    private String email;
}

package com.ohgiraffers.semiproject.adoption.model.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdoptionDTO {

    private String adoptNo;
    private String adoptName;
    private String adoptEmail;
    private String adoptPhone;
    private String adoptAge;
    private String adoptGender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String adoptStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String adoptEndDate;
    private String animalCode;
    private String empCode;
}

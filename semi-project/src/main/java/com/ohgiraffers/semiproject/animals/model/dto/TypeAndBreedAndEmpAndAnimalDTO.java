package com.ohgiraffers.semiproject.animals.model.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TypeAndBreedAndEmpAndAnimalDTO {


    private String animalCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rescueDate;
    private String rescueLocation;
    private String animalImage;
    private int age;
    private String gender;
    private String animalStatus;
    private String typeCode;
    private String breedCode;
    private String empCode;
    private TypeDTO typeDTO;
    private BreedDTO breedDTO;
    private EmpDTO empDTO;

}

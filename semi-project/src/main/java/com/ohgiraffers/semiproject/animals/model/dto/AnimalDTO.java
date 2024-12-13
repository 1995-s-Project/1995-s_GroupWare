package com.ohgiraffers.semiproject.animals.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AnimalDTO {

    private String animalCode;
    private Date rescueDate;
    private String rescueLocation;
    private String animalImage;
    private int age;
    private String gender;
    private String animalStatus;
    private String typeCode;
    private String breedCode;
    private String empCode;

}

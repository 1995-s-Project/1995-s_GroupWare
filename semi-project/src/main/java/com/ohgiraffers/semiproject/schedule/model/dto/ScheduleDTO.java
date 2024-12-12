package com.ohgiraffers.semiproject.schedule.model.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleDTO {
    private String code;
    private String workType;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private Date workDate;
    private LocalDateTime modifyTime;
    private String division;
    private String workModifyReason;
    private String managerIdea;
    private String progressCode;
}

package com.ohgiraffers.semiproject.schedule.model.dao;

import com.ohgiraffers.semiproject.schedule.model.dto.ScheduleDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduleMapper {

    // 출근시간 저장
    void insertWorkStartTime(ScheduleDTO scheduleDTO);

    // 퇴근시간 저장
    void insertWorkEndTime(ScheduleDTO scheduleDTO);

    List<ScheduleDTO> getSchedulesByUserCode(String userCode);
}

package com.ohgiraffers.semiproject.schedule.model.service;

import com.ohgiraffers.semiproject.schedule.model.dao.ScheduleMapper;
import com.ohgiraffers.semiproject.schedule.model.dto.CheckInResponseDTO;
import com.ohgiraffers.semiproject.schedule.model.dto.CheckOutResponseDTO;
import com.ohgiraffers.semiproject.schedule.model.dto.ScheduleDTO;
import com.ohgiraffers.semiproject.schedule.model.dto.VacationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Transactional
    public void insertWorkStartTime(ScheduleDTO scheduleDTO) {

        scheduleMapper.insertWorkStartTime(scheduleDTO);
    }

    @Transactional
    public void insertWorkEndTime(ScheduleDTO scheduleDTO) {

        scheduleMapper.insertWorkEndTime(scheduleDTO);
    }

    public List<ScheduleDTO> getSchedulesByUserCode(String userCode) {

        return scheduleMapper.getSchedulesByUserCode(userCode);
    }

    public LocalDateTime getCheckInTime(String userCode, LocalDateTime localDateTime) {

        CheckInResponseDTO checkInResponseDTO = scheduleMapper.getCheckInTime(userCode, localDateTime);

        return checkInResponseDTO.getCheckInTime();
    }

    public LocalDateTime getCheckOutTime(String userCode, LocalDateTime localDateTime) {

        CheckOutResponseDTO checkOutResponseDTO = scheduleMapper.getCheckOutTime(userCode, localDateTime);

        return checkOutResponseDTO.getCheckOutTime();
    }

    public VacationDTO getVacation(String userCode) {

        VacationDTO vacationDTO =  scheduleMapper.getVacation(userCode);

        return vacationDTO;
    }
}

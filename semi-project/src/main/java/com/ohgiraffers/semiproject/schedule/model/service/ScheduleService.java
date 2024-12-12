package com.ohgiraffers.semiproject.schedule.model.service;

import com.ohgiraffers.semiproject.schedule.model.dao.ScheduleMapper;
import com.ohgiraffers.semiproject.schedule.model.dto.ScheduleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

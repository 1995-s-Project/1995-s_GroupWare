package com.ohgiraffers.semiproject.approval.model.dao;

import com.ohgiraffers.semiproject.manager.model.dto.CacPaymentDTO;
import com.ohgiraffers.semiproject.manager.model.dto.OverTimeDTO;
import com.ohgiraffers.semiproject.manager.model.dto.RetirementDTO;
import com.ohgiraffers.semiproject.manager.model.dto.VacPaymentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApprovalMapper {
    void insertVacForm(VacPaymentDTO vacPaymentDTO);

    void insertCacForm(CacPaymentDTO cacPaymentDTO);

    void insertOverTimeForm(OverTimeDTO overTimeDTO);

    void insertRetireMentForm(RetirementDTO retirementDTO);
}

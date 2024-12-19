package com.ohgiraffers.semiproject.adoption.model.dao;

import com.ohgiraffers.semiproject.adoption.model.dto.AdoptionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdoptionMapper {
    // 입양진행중 조회
    List<AdoptionDTO> adoptingList();
    // 입양완료 조회
    List<AdoptionDTO> adoptCompletedList();
    // 입양취소 조회
    List<AdoptionDTO> adoptCanceledList();
    // 입양진행중 탭에서 입양취소로 상태 업데이트
    void updateByCanceled(String adoptNo);

    // 입양진행중 상세페이지
    AdoptionDTO adoptingDetail(String adoptNo);
    // 입양진행중 상세페이지에서 입양완료로 상태 업데이트
    void updateByCompleted(String adoptNo);

}

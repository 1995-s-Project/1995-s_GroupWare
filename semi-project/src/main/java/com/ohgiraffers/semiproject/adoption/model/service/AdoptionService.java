package com.ohgiraffers.semiproject.adoption.model.service;

import com.ohgiraffers.semiproject.adoption.model.dao.AdoptionMapper;
import com.ohgiraffers.semiproject.adoption.model.dto.AdoptionDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdoptionService {

    private final AdoptionMapper adoptionMapper;

    public AdoptionService(AdoptionMapper adoptionMapper){
        this.adoptionMapper = adoptionMapper;
    }

    // 입양진행중 조회
    public List<AdoptionDTO> adoptingList() {
        return adoptionMapper.adoptingList();
    }
    // 입양완료 조회
    public List<AdoptionDTO> adoptCompletedList() {
        return  adoptionMapper.adoptCompletedList();
    }
    // 입양취소 조회
    public List<AdoptionDTO> adoptCanceledList() {
        return adoptionMapper.adoptCanceledList();
    }

    // 입양진행중 탭에서 입양취소로 상태 업데이트
    public void updateByCanceled(String adoptNo) {
        adoptionMapper.updateByCanceled(adoptNo);
    }

    // 입양진행중 상세페이지
    public AdoptionDTO adoptingDetail(String adoptNo) {
        return adoptionMapper.adoptingDetail(adoptNo);
    }
    // 입양진행중 상세페이지에서 입양완료로 상태 업데이트
    public void updateByCompleted(String adoptNo) {
        adoptionMapper.updateByCompleted(adoptNo);
    }
}

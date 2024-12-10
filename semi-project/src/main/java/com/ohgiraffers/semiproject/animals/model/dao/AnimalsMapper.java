package com.ohgiraffers.semiproject.animals.model.dao;

import com.ohgiraffers.semiproject.animals.model.dto.TypeAndBreedAndEmpAndAnimalDTO;
import com.ohgiraffers.semiproject.animals.model.dto.BreedDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnimalsMapper {
    // 구조동물 전체조회
    List<TypeAndBreedAndEmpAndAnimalDTO> AllList(Map<String, Integer> params);
    // 구조동물 전체조회 - 페이징처리
    int getTotalAnimalCount();

    // 동물등록
    void newAnimal(TypeAndBreedAndEmpAndAnimalDTO animalsDTO);
    // 동물등록 - 품종 비동기처리
    List<BreedDTO> findBreed();
    // 동물등록 - 동물등록번호 자동입력
    String findLastAnimalCode();

    // 체크박스 선택 후 삭제
    void deleteBoard(String id);

}

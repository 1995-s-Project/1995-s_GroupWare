package com.ohgiraffers.semiproject.animals.model.service;

import com.ohgiraffers.semiproject.animals.model.dao.AnimalsMapper;
import com.ohgiraffers.semiproject.animals.model.dto.AnimalDTO;
import com.ohgiraffers.semiproject.animals.model.dto.BreedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AnimalsService {

    public final AnimalsMapper animalsMapper;

    @Autowired
    public AnimalsService(AnimalsMapper animalsMapper){
        this.animalsMapper = animalsMapper;
    }

    // 구조동물 전체조회 및 검색 동물조회
    public List<AnimalDTO> allAnimalAndSearchAnimals(int page, int limit, String animalCode, String typeCode, String breedCode, String gender, Date rescueDate) {

        int offset = (page - 1) * limit;

        return animalsMapper.AllAnimalAndSearchAnimals(offset, limit, animalCode, typeCode, breedCode, gender, rescueDate);
    }

    // 구조동물 전체조회 및 검색 동물조회 - 동물카운트
    public int getTotalAnimalCount(String animalCode, String typeCode, String breedCode, String gender, Date rescueDate) {
        return animalsMapper.getTotalAnimalCount(animalCode, typeCode, breedCode, gender, rescueDate);
    }

    // 구조동물 상세페이지
    public AnimalDTO detailAnimal(String animalCode) {

        return animalsMapper.detailAnimal(animalCode);
    }

    // 동물등록 - 품종 비동기처리
    public List<BreedDTO> findBreed() {
        return animalsMapper.findBreed();
    }
    // 동물등록 - 동물등록번호 자동입력
    public String autoAnimalCode() {
        String lastAnimalCode = animalsMapper.findLastAnimalCode();

        if (lastAnimalCode != null) {
            int lastNumber = Integer.parseInt(lastAnimalCode.substring(1));  // 'A' 제외한 숫자만 추출
            int nextNumber = lastNumber + 1;  // 1을 더함
            return "A" + String.format("%03d", nextNumber);  // 'A'와 3자리 숫자로 반환
        } else {
            return "A0010";  // 첫 번째 동물 코드
        }
    }
    // 동물등록
    public void newAnimal(AnimalDTO typeAndBreedAndEmpAndAnimalDTO) {
        animalsMapper.newAnimal(typeAndBreedAndEmpAndAnimalDTO);
    }

    // 동물등록 - 파일이름 DB에 저장
    public void saveAnimalImageName(String animalImage) {
        animalsMapper.saveAnimalImageName(animalImage);
    }

    // 체크박스 선택 후 삭제
    @Transactional
    public void deleteBoard(String id) {
        animalsMapper.deleteBoard(id);
    }

    // 동물 상태 입양완료로 update
    public void adoptComplete(String code) {
        animalsMapper.adoptComplete(code); // 여러 동물 코드 한번에 처리
    }

    // 입양완료 페이지
    public List<AnimalDTO> adoptAnimalList(int page, int limit) {
        int offset = (page - 1) * limit; // 페이지 시작점 계산
        Map<String, Integer> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return animalsMapper.adoptAnimalList(params);
    }
    // 입양완료 페이징처리
    public int getTotalAdoptAnimalCount() {
        return animalsMapper.getTotalAdoptAnimalCount();
    }

    public void giveUpComplete(String[] animalCodes) {
        List<String> codeList = Arrays.asList(animalCodes); // 배열을 리스트로 변환
        animalsMapper.giveUp(codeList); // 여러 동물 코드 한번에 처리
    }


    public AnimalDTO healthAnimal(String animalCode) {
        return animalsMapper.healthAnimal(animalCode);
    }

    public AnimalDTO inoculationAnimal(String animalCode) {
        return animalsMapper.inoculationAnimal(animalCode);
    }
}

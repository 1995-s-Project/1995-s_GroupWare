package com.ohgiraffers.semiproject.animals.model.service;

import com.ohgiraffers.semiproject.animals.model.dao.AnimalsMapper;
import com.ohgiraffers.semiproject.animals.model.dto.TypeAndBreedAndEmpAndAnimalDTO;
import com.ohgiraffers.semiproject.animals.model.dto.BreedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnimalsService {

    public final AnimalsMapper animalsMapper;

    @Autowired
    public AnimalsService(AnimalsMapper animalsMapper){
        this.animalsMapper = animalsMapper;
    }

    // 구조동물 전체조회
    public List<TypeAndBreedAndEmpAndAnimalDTO> getPagedAnimalList(int page, int limit) {
        int offset = (page - 1) * limit; // 페이지 시작점 계산
        Map<String, Integer> params = new HashMap<>();
        params.put("limit", limit);
        params.put("offset", offset);
        return animalsMapper.AllList(params);
    }
    // 구조동물 전체조회 - 페이징처리
    public int getTotalAnimalCount() {
        return animalsMapper.getTotalAnimalCount();
    }

    // 동물등록
    @Transactional
    public void newAnimal(TypeAndBreedAndEmpAndAnimalDTO animalsDTO) {
        animalsMapper.newAnimal(animalsDTO);
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

    // 체크박스 선택 후 삭제
    @Transactional
    public void deleteBoard(String id) {
        animalsMapper.deleteBoard(id);
    }

    


}

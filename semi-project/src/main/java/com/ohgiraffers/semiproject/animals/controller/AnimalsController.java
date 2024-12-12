package com.ohgiraffers.semiproject.animals.controller;

import com.ohgiraffers.semiproject.animals.model.dto.TypeAndBreedAndEmpAndAnimalDTO;
import com.ohgiraffers.semiproject.animals.model.dto.BreedDTO;
import com.ohgiraffers.semiproject.animals.model.service.AnimalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class AnimalsController {

    private final AnimalsService animalsService;

    @Autowired
    public AnimalsController(AnimalsService animalsService){
        this.animalsService = animalsService;
    }

    // 구조 동물 페이지
    @GetMapping("/sidemenu/animals")
    public String animals(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int limit,
                          Model model){

        List<TypeAndBreedAndEmpAndAnimalDTO> list = animalsService.getPagedAnimalList(page, limit);
        model.addAttribute("list", list);

        // 페이지 네비게이션 정보 추가
        int totalRecords = animalsService.getTotalAnimalCount(); // 총 레코드 수 조회
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "sidemenu/animals/animals";
    }

    // 동물등록번호 등록 시 자동부여
    @GetMapping("/sidemenu/animals/insert")
    public String addAnimal(Model model){
        String animalCode = animalsService.autoAnimalCode();

        model.addAttribute("animalCode", animalCode);
        return "sidemenu/animals/insert";
    }
    @GetMapping(value = "/sidemenu/animals/breed", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<BreedDTO> findCategoryList(){
        return animalsService.findBreed();
    }
    // 구조동물 등록
    @PostMapping("/sidemenu/animals/insert")
    public String newAnimal(@ModelAttribute TypeAndBreedAndEmpAndAnimalDTO animalsDTO,
                            @RequestParam(value = "image", required = false) MultipartFile image){
        System.out.println("image = " + image);
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);  // 이미지 저장 메서드 호출
            animalsDTO.setAnimalImage(imagePath);
        } else {
            animalsDTO.setAnimalImage("defaultAnimal.jpg");  // 기본 이미지 설정
        }

        animalsService.newAnimal(animalsDTO);
        return "redirect:/sidemenu/animals";
    }
    // 구조동물 사진등록 메소드
    private String saveImage(MultipartFile image) {
        try {
            String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/animalimages/";  // 실제 경로로 변경해야 합니다
            String fileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
            File targetFile = new File(uploadDir, fileName);

            // 디렉토리가 없다면 생성
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            // 이미지 저장
            image.transferTo(targetFile);

            // 저장된 이미지 경로 반환
            return fileName;  // 웹에서 접근할 수 있는 경로
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // 오류 처리
        }
    }

    // 체크박스 선택 후 삭제
    @PostMapping("/delete")
    public String delete(@RequestParam List<String> boardIds){

        for(int i=0; i<boardIds.size(); i++){
            String id = boardIds.get(i);
            animalsService.deleteBoard(id);
            System.out.println("id = " + id);
        }
        
        return "redirect:/sidemenu/animals";
    }

    // 입양완료 페이지로 이동
    @GetMapping("/sidemenu/adoptionComplete")
    public String adoptionComplete(){
        return "sidemenu/animals/adoptionComplete";
    }

    // 재고관리 페이지로 이동
    @GetMapping("/sidemenu/stock")
    public String stock(){
        return "sidemenu/animals/stock";
    }
}

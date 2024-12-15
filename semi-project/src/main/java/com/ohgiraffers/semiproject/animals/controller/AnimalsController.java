package com.ohgiraffers.semiproject.animals.controller;

import com.ohgiraffers.semiproject.animals.model.dto.AnimalDTO;
import com.ohgiraffers.semiproject.animals.model.dto.BreedDTO;
import com.ohgiraffers.semiproject.animals.model.dto.InventoryDTO;
import com.ohgiraffers.semiproject.animals.model.service.AnimalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class AnimalsController {

    private final AnimalsService animalsService;

    @Autowired
    public AnimalsController(AnimalsService animalsService){
        this.animalsService = animalsService;
    }

// -----------------------------------------구조동물 페이지-----------------------------------------
    // 구조 동물 페이지
    @GetMapping("/sidemenu/animals")
    public String animals(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int limit,
                          @RequestParam(required = false) String animalCode,
                          @RequestParam(required = false) String typeCode,
                          @RequestParam(required = false) String breedCode,
                          @RequestParam(required = false) String gender,
                          @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date rescueDate,
                          Model model) {

        List<AnimalDTO> list = animalsService.allAnimalAndSearchAnimals(page, limit, animalCode, typeCode, breedCode, gender, rescueDate);
        model.addAttribute("list", list);

        // 페이지 네비게이션 정보 추가
        int totalRecords = animalsService.getTotalAnimalCount(animalCode, typeCode, breedCode, gender, rescueDate); // 총 레코드 수 조회
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("limit", limit);

        return "sidemenu/animals/animals";
    }

    // 구조동물 상세페이지
    @GetMapping("/animals/detailAnimal/{animalCode}")
    public String detailAnimal(@PathVariable String animalCode,Model model){

        AnimalDTO detail = animalsService.detailAnimal(animalCode);
        model.addAttribute("detail", detail);

        AnimalDTO health = animalsService.healthAnimal(animalCode);
        model.addAttribute("health", health);

        AnimalDTO inoculate = animalsService.inoculationAnimal(animalCode);
        model.addAttribute("inoculate", inoculate);

        return "sidemenu/animals/detailAnimal";
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
    public String newAnimal(@ModelAttribute AnimalDTO typeAndBreedAndEmpAndAnimalDTO){

        animalsService.newAnimal(typeAndBreedAndEmpAndAnimalDTO);
        return "redirect:/sidemenu/animals";
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

    // 입양완료으로 상태 수정
    @PostMapping("/adoptionComplete")
    public String adoptionComplete(@RequestParam List<String> animalCode) {

        
        for(int i=0; i<animalCode.size(); i++){
            String code = animalCode.get(i);
            animalsService.adoptComplete(code);
        }

        return "redirect:/sidemenu/animals";
    }

// -----------------------------------------입양완료 페이지-----------------------------------------
    // 입양완료 페이지
    @GetMapping("/sidemenu/adoptionComplete")
    public String adoptionComplete(@RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int limit,
                                   Model model){
        List<AnimalDTO> adoptList = animalsService.adoptAnimalList(page, limit);
        model.addAttribute("adoptList", adoptList);

        // 페이지 네비게이션 정보 추가
        int totalRecords = animalsService.getTotalAdoptAnimalCount(); // 총 레코드 수 조회
        int totalPages = (int) Math.ceil((double) totalRecords / limit);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

        return "sidemenu/animals/adoptionComplete";
    }

    // 구조동물 상세페이지
    @GetMapping("/adoptionComplete/detailAnimal/{animalCode}")
    public String adoptionCompleteDetailAnimal(@PathVariable String animalCode,Model model){

        AnimalDTO detail = animalsService.adoptionDetailAnimal(animalCode);
        model.addAttribute("detail", detail);

        AnimalDTO health = animalsService.adoptionHealthAnimal(animalCode);
        model.addAttribute("health", health);

        AnimalDTO inoculate = animalsService.adoptionInoculationAnimal(animalCode);
        model.addAttribute("inoculate", inoculate);

        return "sidemenu/animals/adoptionDetailAnimal";
    }


    // 파양으로 상태 수정
    @GetMapping("/giveUp")
    public String giveUp(@RequestParam String animalCode){

        // 쉼표로 구분된 코드들을 배열로 변환
        String[] animalCodes = animalCode.split(",");

        // 입양 완료 상태로 업데이트
        animalsService.giveUpComplete(animalCodes);

        return "redirect:/sidemenu/adoptionComplete";
    }

// -----------------------------------------재고관리 페이지-----------------------------------------
    // 재고관리 페이지로 이동
    @GetMapping("/sidemenu/stock")
    public String stock(Model model){

        List<InventoryDTO> inventoryList = animalsService.stock();
        System.out.println("inventoryList = " + inventoryList);
        model.addAttribute("inventoryList", inventoryList);

        return "sidemenu/animals/stock";
    }
}

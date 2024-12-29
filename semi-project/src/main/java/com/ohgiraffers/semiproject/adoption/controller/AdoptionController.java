package com.ohgiraffers.semiproject.adoption.controller;

import com.ohgiraffers.semiproject.adoption.model.dto.AdoptionDTO;
import com.ohgiraffers.semiproject.adoption.model.service.AdoptionService;
import com.ohgiraffers.semiproject.animals.model.dto.AnimalDTO;
import com.ohgiraffers.semiproject.animals.model.dto.BreedDTO;
import com.ohgiraffers.semiproject.animals.model.service.AnimalsService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final AnimalsService animalsService;
    private final UserInfoService userInfoService;

    @Autowired
    public AdoptionController(AdoptionService adoptionService, AnimalsService animalsService, UserInfoService userInfoService){
        this.adoptionService = adoptionService;
        this.animalsService = animalsService;
        this.userInfoService = userInfoService;
    }

/* comment.--------------------------------- 입양진행중 Tab --------------------------------- */

    // 입양 페이지로 이동
    @GetMapping("/adoption")
    public String adoptionList(Model model){

        UserInfoResponse userInfo =  userInfoService.getUserInfo();
        String code = userInfo.getCode();
        model.addAttribute("code", code);

        List<AdoptionDTO> adoptingList = adoptionService.adoptingList();
        model.addAttribute("adoptingList", adoptingList);

        List<AdoptionDTO> adoptCompletedList = adoptionService.adoptCompletedList();
        model.addAttribute("adoptCompletedList", adoptCompletedList);

        List<AdoptionDTO> adoptCanceledList = adoptionService.adoptCanceledList();
        model.addAttribute("adoptCanceledList", adoptCanceledList);

        String adoptNo = adoptionService.addAdoptNo();
        model.addAttribute("adoptNo", adoptNo);

        return "sidemenu/adoption/adoption";
    }

    // 입양진행중 탭에서 입양취소로 상태 업데이트
    @GetMapping("/adopterCanceled/{adoptNo}")
    public String updateByCanceled(@PathVariable String adoptNo){

        adoptionService.updateByCanceled(adoptNo);

        return "redirect:/adoption?tab=canceled";
    }

    // 입양진행중 상세페이지
    @GetMapping("/adopterDetail/adopting/{adoptNo}")
    public String adoptingAdopterDetail(@PathVariable String adoptNo, Model model){

        AdoptionDTO adoptingDetail = adoptionService.adoptingDetail(adoptNo);
        model.addAttribute("adoptingDetail", adoptingDetail);

        return "sidemenu/adoption/adoptingDetail";
    }

    // 입양진행중 상세페이지에서 입양완료로 상태 업데이트
    @GetMapping("/adoptCompleted/{adoptNo}")
    public String updateByCompleted(@PathVariable String adoptNo){

        adoptionService.updateByCompleted(adoptNo);

        return "redirect:/adoption?tab=completed";
    }

    // 입양등록 - 동물등록번호 비동기 처리
    @GetMapping(value = "/adoption/animalsCode", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<AnimalDTO> findAnimalsCodeList(){
        return animalsService.findAnimalCodes();
    }
    // 입양 등록
    @PostMapping("/adoption/insert")
    public ResponseEntity<String> insertAdoption(@ModelAttribute AdoptionDTO adoptionDTO){

        if (adoptionDTO.getAdoptStatus() == null) {
            adoptionDTO.setAdoptStatus("입양진행중");  // 기본값 설정
        }

        adoptionService.insertAdoption(adoptionDTO);

        return ResponseEntity.ok("등록 성공");
    }

/* comment.--------------------------------- 입양완료 Tab --------------------------------- */
    // 입양완료 상세페이지
    @GetMapping("/adopterDetail/completed/{adoptNo}")
    public String completedAdopterDetail(@PathVariable String adoptNo, Model model){

        AdoptionDTO completedDetail = adoptionService.completedAdopterDetail(adoptNo);
        model.addAttribute("completedDetail", completedDetail);

        return "sidemenu/adoption/adoptCompletedDetail";
    }

    // 입양완료 상세페이지에서 파양으로 상태 업데이트
    @GetMapping("/giveUp/{adoptNo}")
    public String updateByGiveUp(@PathVariable String adoptNo){

        adoptionService.updateByGiveUp(adoptNo);

        return "redirect:/adoption?tab=canceled";
    }

/* comment.--------------------------------- 입양취소 Tab --------------------------------- */
    // 입양취소 상세페이지
    @GetMapping("/adopterDetail/canceled/{adoptNo}")
    public String canceledAdopterDetail(@PathVariable String adoptNo, Model model){

        AdoptionDTO canceledDetail = adoptionService.canceledAdopterDetail(adoptNo);
        model.addAttribute("canceledDetail", canceledDetail);

        return "sidemenu/adoption/adoptCanceledDetail";
    }
}

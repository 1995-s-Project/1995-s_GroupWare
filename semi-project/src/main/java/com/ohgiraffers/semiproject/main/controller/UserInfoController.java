package com.ohgiraffers.semiproject.main.controller;

import com.ohgiraffers.semiproject.home.auth.model.dto.AuthDetailes;
import com.ohgiraffers.semiproject.home.model.dto.LoginUserDTO;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    @GetMapping("/user/info")
    public UserInfoResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("authentication = " + authentication);

        if (authentication != null && authentication.isAuthenticated()) {
            AuthDetailes userDetails = (AuthDetailes) authentication.getPrincipal();

            // 이름과 프로필 이미지 URL을 가져옵니다.
            String code = userDetails.getUsername();
            LoginUserDTO loginUserDTO = userDetails.getLoginUserDTO();
            String profilePictureUrl = loginUserDTO.getImage(); // 프로필 이미지 URL 가져오기
            String name = loginUserDTO.getName(); // 이름 가져오기 (AuthDetailes에 해당 메서드가 있어야 함)
            String department = loginUserDTO.getDeptCode(); // 부서명 가져오기 (LoginUserDTO에 해당 메서드가 있어야 함)
            String position = loginUserDTO.getJobCode(); // 직급 가져오기 (LoginUserDTO에 해당 메서드가 있어야 함)

            return new UserInfoResponse(code, profilePictureUrl, name, department, position);

        }
        return null; // 또는 적절한 에러 처리
    }
}

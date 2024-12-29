package com.ohgiraffers.semiproject.mypage.controller;

import com.ohgiraffers.semiproject.animals.model.dto.AnimalDTO;
import com.ohgiraffers.semiproject.animals.model.service.AnimalsService;
import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import com.ohgiraffers.semiproject.employee.model.service.EmployeeService;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import com.ohgiraffers.semiproject.main.model.service.UserInfoService;
import com.ohgiraffers.semiproject.mypage.model.service.MyPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MyPageController {

    private final UserInfoService userInfoService;
    private final MyPageService myPageService;
    private final EmployeeService employeeService;
    private final AnimalsService animalsService;

    public MyPageController(UserInfoService userInfoService, MyPageService myPageService, EmployeeService employeeService, AnimalsService animalsService){
        this.userInfoService = userInfoService;
        this.myPageService = myPageService;
        this.employeeService = employeeService;
        this.animalsService = animalsService;
    }

    // 마이페이지 페이지로 이동 및 회원정보 수정
    @GetMapping("/mypage")
    public String mypage(Model model) {
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String empCode = userInfo.getCode();

        EmployeeJoinListDTO info = employeeService.empInfoSelect(empCode);

        model.addAttribute("info", info);

        return "sidemenu/mypage/mypage";
    }

    // 회원정보수정 - 프로필이미지 수정
    @PostMapping("/updateProfileImage")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateProfileImage(@RequestParam("fileName") String fileName){
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String empCode = userInfo.getCode();

        myPageService.changeProfileImage(fileName, empCode);

        Map<String, String> response = new HashMap<>();
        response.put("updateMessage", "수정이 완료되었습니다.");

        return ResponseEntity.ok(response);
    }

    // 회원정보수정 - 프로필이미지 삭제
    @GetMapping("deleteProfileImage")
    public String deleteProfileImage(RedirectAttributes redirectAttributes){
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String empCode = userInfo.getCode();

        boolean deleteProfile = myPageService.deleteProfileImage(empCode);
        redirectAttributes.addFlashAttribute("deleteProfile", deleteProfile);

        return "redirect:/mypage";
    }

    // 회원정보수정 - 주소 수정
    @PostMapping("/updateAddress")
    public String updateAddress(@RequestBody Map<String, String> request) {
        String newAddress = request.get("empAddress");

        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String empCode = userInfo.getCode();

        myPageService.changeAddress(newAddress, empCode);
        return "redirect:/mypage";
    }

    // 회원정보수정 - 휴대전화번호 수정
    @PostMapping("/updatePhoneNumber")
    public String updatePhoneNumber(@RequestBody Map<String, String> request) {
        String newPhone = request.get("newPhoneNumber");

        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String empCode = userInfo.getCode();

        myPageService.changePhone(newPhone, empCode);
        return "redirect:/mypage";
    }

    // 마이페이지 - 비밀번호 변경
    @GetMapping("/setting/password")
    public String settingPassWord(){
        return "sidemenu/mypage/settingPass";
    }

    @PostMapping("/setting/password")
    @ResponseBody
    public ResponseEntity<Map<String, String>> settingPassWord(@RequestParam String newPW, @RequestParam String confirmPW, Model model) {

        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String code = userInfo.getCode();
        // 새 비밀번호를 변경하는 로직 (예시: 암호화 후 저장)
        myPageService.changePassword(newPW, code);

        Map<String, String> response = new HashMap<>();
        response.put("updateMessage", "비밀번호 변경이 성공적으로 완료되었습니다.\n새로운 비밀번호로 다시 로그인 해주세요.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-password")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> checkPassword(@RequestBody Map<String, String> request) {
        String enteredPassword = request.get("password");

        // 현재 비밀번호와 입력된 비밀번호 비교
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String currentPassword = userInfo.getPass(); // 저장된 비밀번호 (DB에서 가져온 값)

        boolean isValid = myPageService.checkCurrentPassword(enteredPassword, currentPassword);

        // 결과를 클라이언트로 전달
        Map<String, Object> response = new HashMap<>();
        response.put("isValid", isValid);

        return ResponseEntity.ok(response);
    }


    // 마이페이지 - 댓글내역
    @GetMapping("/myActivity/comments")
    public String myComments(Model model) {

        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String code = userInfo.getCode();
       
        List<CommentDTO> comments = employeeService.getUserComment(code);
        
        model.addAttribute("comments", comments);
        return "sidemenu/mypage/myComments";
    }

    @GetMapping("/myActivity/posts")
    public String myPosts(Model model) {
        UserInfoResponse userInfo = userInfoService.getUserInfo();
        String code = userInfo.getCode();
        List<AnimalDTO> posts = animalsService.getUserPosts(code);

        model.addAttribute("posts", posts);
        return "sidemenu/mypage/myPosts";
    }
}

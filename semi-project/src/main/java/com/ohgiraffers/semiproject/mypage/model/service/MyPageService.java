package com.ohgiraffers.semiproject.mypage.model.service;

import com.ohgiraffers.semiproject.employee.model.dao.EmployeeMapper;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.semiproject.main.model.dto.UserInfoResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final PasswordEncoder passwordEncoder;
    private final EmployeeMapper employeeMapper;


    public MyPageService(PasswordEncoder passwordEncoder, EmployeeMapper employeeMapper){
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    public void changePassword(String newPW, String code) {

        // 새 비밀번호 암호화
        String encryptedPassword = encoder.encode(newPW);

        // 사용자의 비밀번호를 암호화된 새 비밀번호로 업데이트
        employeeMapper.updatePasswordByCode(encryptedPassword, code);
    }

    public boolean checkCurrentPassword(String enteredPassword, String currentPassword) {
        return passwordEncoder.matches(enteredPassword, currentPassword);
    }


    // 회원정보수정 - 프로필이미지 수정
    public void changeProfileImage(String fileName, String empCode) {

        employeeMapper.changeProfileImage(fileName, empCode);
    }

    // 회원정보수정 - 프로필이미지 삭제
    public void deleteProfileImage(String empCode) {
        employeeMapper.deleteProfileImage(empCode);
    }

    // 회원정보수정 - 주소 수정
    public void changeAddress(String newAddress, String empCode) {
        employeeMapper.changeAddress(newAddress, empCode);
    }

    // 회원정보수정 - 휴대전화번호 수정
    public void changePhone(String newPhone, String empCode) {
        employeeMapper.changePhone(newPhone, empCode);
    }
}

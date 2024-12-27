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

    public boolean changePassword(String newPW, String code) {

        // 새 비밀번호 암호화
        String encryptedPassword = encoder.encode(newPW);

        // 사용자의 비밀번호를 암호화된 새 비밀번호로 업데이트
        int passwordUpdate = employeeMapper.updatePasswordByCode(encryptedPassword, code);

        return passwordUpdate > 0;
    }

    public boolean checkCurrentPassword(String enteredPassword, String currentPassword) {
        return passwordEncoder.matches(enteredPassword, currentPassword);
    }


    // 회원정보수정 - 프로필이미지 수정
    public boolean changeProfileImage(String fileName, String empCode) {

        int updateProfile = employeeMapper.changeProfileImage(fileName, empCode);

        return updateProfile > 0;
    }

    // 회원정보수정 - 프로필이미지 삭제
    public boolean deleteProfileImage(String empCode) {
        int deleteProfile = employeeMapper.deleteProfileImage(empCode);

        return deleteProfile > 0;
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

package com.ohgiraffers.semiproject.employee.model.service;

import com.ohgiraffers.semiproject.employee.model.dao.EmployeeMapper;
import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeScheduleInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeDTOJOB> empAll(int offset, int size) {

        List<EmployeeDTOJOB> result = employeeMapper.empAll(offset, size);

        return result;
    }

    public EmployeeDTOJOB empSelect(String empCode) {

        return employeeMapper.empSelect(empCode);
    }
    @Transactional
    public void saveComment(CommentDTO commentDTO) {

        employeeMapper.saveComment(commentDTO);
    }

    public List<CommentDTO> comment(String empCode) {

        return employeeMapper.comment(empCode);
    }


    public long getTotalProducts() {

        return employeeMapper.countAll();
    }

    public List<EmployeeJoinListDTO> empAllSelect() {
        return employeeMapper.empAllSelect();
    }

    @Transactional
    public void commentDelete(CommentDTO commentDTO) {

        employeeMapper.commentDelete(commentDTO);
    }

    // 마이페이지 - 내 댓글 조회 메소드
    public List<CommentDTO> getUserComment(String code) {return employeeMapper.getUserComment(code);}
    // 마이페이지 - 비밀번호 변경
    @Transactional
    public List<EmployeeJoinListDTO> mailAllSelect(String code) {return employeeMapper.mailAllSelect(code);}
    // 마이페이지 - 회원정보 수정
    @Transactional
    public EmployeeJoinListDTO empInfoSelect(String empCode) {return employeeMapper.empInfoSelect(empCode);}

    // 직원별 사번코드, 부서명, 직급명, 당일 출근시간, 퇴근시간 조회해오기
    public List<EmployeeScheduleInfoDTO> getEmployeeList() {
        return  employeeMapper.getEmployeeList();
    }

    // 직원 사번 코드 가져오기
    public List<EmployeeScheduleInfoDTO> getEmployeeCode() {
        return  employeeMapper.getEmployeeCode();
    }
    // 경영부 전체조회
    public List<EmployeeDTOJOB> managementDept() {

        return employeeMapper.managementDept();
    }
    // 애견담당부 전체조회
    public List<EmployeeDTOJOB> dogDept() {

        return employeeMapper.dogDept();
    }
    // 마케팅부 전체조회
    public List<EmployeeDTOJOB> marketingDept() {

        return employeeMapper.marketingDept();
    }
    // 영업부 전체조회
    public List<EmployeeDTOJOB> salesDept() {

        return employeeMapper.salesDept();
    }
    // 물류부 전체조회
    public List<EmployeeDTOJOB> logisticsDept() {

        return employeeMapper.logisticsDept();
    }

    public List<EmployeeDTOJOB> customerDept() {

        return employeeMapper.customerDept();
    }

    public List<EmployeeDTOJOB> empAllList() {
        return employeeMapper.empAllList();
    }
}

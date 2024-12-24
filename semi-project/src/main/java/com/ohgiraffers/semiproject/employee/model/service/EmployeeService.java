package com.ohgiraffers.semiproject.employee.model.service;

import com.ohgiraffers.semiproject.employee.model.dao.EmployeeMapper;
import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
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

    public List<EmployeeDTOJOB> empSearch(String query, int offset, int size) {

        List<EmployeeDTOJOB> result = employeeMapper.empSearch(query, offset, size);

        return result;
    }

    public long getTotalProducts1(String query) {

        return employeeMapper.countAll1(query);
    }

    public List<EmployeeJoinListDTO> empAllSelect() {
        return employeeMapper.empAllSelect();
    }

    @Transactional
    public void commentDelete(CommentDTO commentDTO) {

        employeeMapper.commentDelete(commentDTO);
    }

    // 마이페이지 - 내 댓글 조회 메소드
    public List<CommentDTO> getUserComment(String code) {
        return employeeMapper.getUserComment(code);
    }

    public List<EmployeeJoinListDTO> mailAllSelect(String code) {

        return employeeMapper.mailAllSelect(code);
    }
}

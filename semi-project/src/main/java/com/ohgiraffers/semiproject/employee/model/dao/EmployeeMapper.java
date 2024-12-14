package com.ohgiraffers.semiproject.employee.model.dao;

import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<EmployeeDTOJOB> empAll();

    EmployeeDTOJOB empSelect(Integer empCode);

    void saveComment(CommentDTO commentDTO);

    List<CommentDTO> comment(Integer empCode);

    List<EmployeeJoinListDTO> empAllSelect();
}

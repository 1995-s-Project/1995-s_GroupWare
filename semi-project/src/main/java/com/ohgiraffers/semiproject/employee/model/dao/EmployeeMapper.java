package com.ohgiraffers.semiproject.employee.model.dao;

import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    List<EmployeeDTO> selectemp();
}

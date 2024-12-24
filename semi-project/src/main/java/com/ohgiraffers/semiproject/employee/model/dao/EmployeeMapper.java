package com.ohgiraffers.semiproject.employee.model.dao;

import com.ohgiraffers.semiproject.employee.model.dto.CommentDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTOJOB;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeJoinListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    List<EmployeeDTOJOB> empAll(@Param("offset") int offset, @Param("size") int size);

    long countAll();

    List<EmployeeDTOJOB> empSearch(String query, @Param("offset") int offset,
                                                 @Param("size") int size);

    long countAll1(String query);

    EmployeeDTOJOB empSelect(String empCode);

    void saveComment(CommentDTO commentDTO);

    List<CommentDTO> comment(String empCode);

    List<EmployeeJoinListDTO> empAllSelect();

    void commentDelete(CommentDTO commentDTO);

    // 마이페이지 - 내 댓글 조회 메소드
    List<CommentDTO> getUserComment(String code);

    List<EmployeeJoinListDTO> mailAllSelect(String code);
}

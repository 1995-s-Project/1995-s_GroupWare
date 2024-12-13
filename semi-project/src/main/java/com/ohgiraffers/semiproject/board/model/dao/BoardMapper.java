package com.ohgiraffers.semiproject.board.model.dao;

import com.ohgiraffers.semiproject.board.model.dto.BoardDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDTO> select();

    List<BoardDTO> search(String query);

    void boardRegist(BoardDTO board);

    void delete(Integer boardCode);

    BoardDTO title(Integer boardCode);

    void update(BoardDTO boardDTO);

    void viewCount(Integer boardCode);
}

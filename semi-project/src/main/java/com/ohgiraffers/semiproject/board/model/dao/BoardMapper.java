package com.ohgiraffers.semiproject.board.model.dao;

import com.ohgiraffers.semiproject.board.model.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDTO> select();

    void boardRegist(BoardDTO board);

    BoardDTO title(String title);

    void delete(Integer boardCode);

    void update(Integer boardCode);

    List<BoardDTO> search(String query);
}

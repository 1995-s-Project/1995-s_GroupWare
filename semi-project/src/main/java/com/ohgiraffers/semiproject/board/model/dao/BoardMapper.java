package com.ohgiraffers.semiproject.board.model.dao;

import com.ohgiraffers.semiproject.board.model.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDTO> selectAll(@Param("offset") int offset, @Param("size") int size);

    long countAll();

    List<BoardDTO> search(String query,
                                 @Param("offset") int offset,
                                 @Param("size") int size);

    long countSearch(String query);

    void boardRegist(BoardDTO board);

    void delete(Integer boardCode);

    BoardDTO title(Integer boardCode);

    void update(BoardDTO boardDTO);

    void viewCount(Integer boardCode);

    List<BoardDTO> select();

}
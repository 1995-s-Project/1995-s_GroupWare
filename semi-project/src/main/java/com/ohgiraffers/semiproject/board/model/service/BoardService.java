package com.ohgiraffers.semiproject.board.model.service;

import com.ohgiraffers.semiproject.board.model.dao.BoardMapper;
import com.ohgiraffers.semiproject.board.model.dto.BoardDTO;
import com.ohgiraffers.semiproject.employee.model.dto.EmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    private final BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public List<BoardDTO> select() {
        return boardMapper.select();
    }

    public List<BoardDTO> selectAll(int offset, int size) {

        List<BoardDTO> result = boardMapper.selectAll(offset, size);

        return result;
    }
    @Transactional
    public void boardRegist(BoardDTO board) {

        boardMapper.boardRegist(board);
    }

    public List<BoardDTO> search(String query) {

        return boardMapper.search(query);
    }

    @Transactional
    public void delete(Integer boardCode) {

        boardMapper.delete(boardCode);
    }

    public BoardDTO title(Integer boardCode) {

        return boardMapper.title(boardCode);
    }

    @Transactional
    public void update(BoardDTO boardDTO) {

        boardMapper.update(boardDTO);
    }

    public void viewConut(Integer boardCode) {

        boardMapper.viewCount(boardCode);
    }

    public long getTotalProducts() {
            return boardMapper.count(); // 총 레코드 수 반환
    }
}

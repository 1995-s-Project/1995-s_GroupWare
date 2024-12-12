package com.ohgiraffers.semiproject.board.model.service;

import com.ohgiraffers.semiproject.board.model.dao.BoardMapper;
import com.ohgiraffers.semiproject.board.model.dto.BoardDTO;
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
    @Transactional
    public void boardRegist(BoardDTO board) {

        boardMapper.boardRegist(board);
    }

    public BoardDTO title(String title) {

        return boardMapper.title(title);
    }

    @Transactional
    public void delete(Integer boardCode) {

        boardMapper.delete(boardCode);
    }


    public void update(Integer boardCode) {

        boardMapper.update(boardCode);
    }

    public List<BoardDTO> search(String query) {

        return boardMapper.search(query);
    }
}

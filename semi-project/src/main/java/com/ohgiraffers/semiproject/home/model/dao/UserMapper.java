package com.ohgiraffers.semiproject.home.model.dao;

import com.ohgiraffers.semiproject.home.model.dto.LoginUserDTO;
import com.ohgiraffers.semiproject.home.model.dto.NoCheckDTO;
import com.ohgiraffers.semiproject.home.model.dto.NoSearchDTO;
import com.ohgiraffers.semiproject.home.model.dto.SignupDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int regist(SignupDTO signupDTO);

    LoginUserDTO findByUsername(String username);

    NoCheckDTO findByNo(NoSearchDTO noCheckDTO);
}

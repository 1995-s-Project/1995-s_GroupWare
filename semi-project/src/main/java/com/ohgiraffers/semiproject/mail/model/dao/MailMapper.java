package com.ohgiraffers.semiproject.mail.model.dao;

import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MailMapper {
    List<MailDTO> mailAllSelect(String code);

    void registMail(MailDTO mailDTO);

    MailDTO mailDetail(Integer emailCode);

    List<MailDTO> mailSentSelect(String code);

    void mailTrash(Integer emailCode);
}

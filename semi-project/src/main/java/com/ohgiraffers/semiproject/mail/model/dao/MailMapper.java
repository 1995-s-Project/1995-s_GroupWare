package com.ohgiraffers.semiproject.mail.model.dao;

import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import com.ohgiraffers.semiproject.mail.model.dto.MailDTO2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface MailMapper {
    List<MailDTO> mailAllSelect(String code);

    void registMail(MailDTO mailDTO);

    MailDTO mailDetail(Integer emailCode);

    List<MailDTO> mailSentSelect(String code);

    List<MailDTO> mailFolderImportant(String code);

    List<MailDTO> mailFolderTrash(String code);

    List<MailDTO> mailFolderArchived(String code);

}


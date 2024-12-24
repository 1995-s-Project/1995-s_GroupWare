package com.ohgiraffers.semiproject.mail.model.dao;

import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MailMapper {
    List<MailDTO> mailAllSelect(String code);

    void registMail(MailDTO mailDTO);

    MailDTO mailDetail(Integer emailCode);

    List<MailDTO> mailSentSelect(String code);

    List<MailDTO> mailFolderImportant(String code);

    List<MailDTO> mailFolderTrash(String code);

    List<MailDTO> mailFolderArchived(String code);

    void moveMails(@Param("mail") List<Integer> mail, @Param("folder") String folder, @Param("code") String code);

    void deleteMails(@Param("mail") List<Integer> mail, @Param("code") String code);

    void sentMoveMails(@Param("mail") List<Integer> mail, @Param("folder") String folder, @Param("code") String code);
}


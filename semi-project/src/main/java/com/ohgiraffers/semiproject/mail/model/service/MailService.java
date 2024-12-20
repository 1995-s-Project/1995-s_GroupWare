package com.ohgiraffers.semiproject.mail.model.service;

import com.ohgiraffers.semiproject.mail.model.dao.MailMapper;
import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import com.ohgiraffers.semiproject.mail.model.dto.MailDTO2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MailService {

    private final MailMapper mailMapper;

    public MailService(MailMapper mailMapper) {
        this.mailMapper = mailMapper;
    }

    public List<MailDTO> mailAllSelect(String code) {

        return mailMapper.mailAllSelect(code);
    }

    public void registMail(MailDTO mailDTO) {

        mailMapper.registMail(mailDTO);
    }

    public MailDTO mailDetail(Integer emailCode) {

        return mailMapper.mailDetail(emailCode);
    }

    public List<MailDTO> mailSentSelect(String code) {

        return mailMapper.mailSentSelect(code);
    }

    public List<MailDTO> mailFolderImportant(String code) {

        return mailMapper.mailFolderImportant(code);
    }

    public List<MailDTO> mailFolderTrash(String code) {

        return mailMapper.mailFolderTrash(code);
    }

    public List<MailDTO> mailFolderArchived(String code) {

        return mailMapper.mailFolderArchived(code);
    }
}




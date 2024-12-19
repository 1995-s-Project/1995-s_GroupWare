package com.ohgiraffers.semiproject.mail.model.service;

import com.ohgiraffers.semiproject.mail.model.dao.MailMapper;
import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void mailTrash(Integer emailCode) {

        mailMapper.mailTrash(emailCode);
    }
}

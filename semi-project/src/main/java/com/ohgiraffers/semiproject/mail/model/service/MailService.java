package com.ohgiraffers.semiproject.mail.model.service;

import com.ohgiraffers.semiproject.mail.model.dao.MailMapper;
import com.ohgiraffers.semiproject.mail.model.dto.MailDTO;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void moveMails(List<Integer> mail, String recipientFolder, String code) {

        if (mail == null || mail.isEmpty()) {
            throw new IllegalArgumentException("메일 ID 목록이 비어 있습니다.");
        }

        mailMapper.moveMails(mail, recipientFolder, code);
    }

    @Transactional
    public void sentMoveMails(List<Integer> mail, String senderFolder, String code) {

        if (mail == null || mail.isEmpty()) {
            throw new IllegalArgumentException("메일 ID 목록이 비어 있습니다.");
        }

        mailMapper.sentMoveMails(mail, senderFolder, code);
    }

    @Transactional
    public void inboxRegistMail(MailDTO mailDTO, List<String> recipientId, List<String> recipientName) {
        for (int i = 0; i < recipientId.size(); i++) {
            mailDTO.setRecipientId(recipientId.get(i));  // 수신자 ID
            mailDTO.setRecipientName(recipientName.get(i));  // 수신자 이름
            mailMapper.inboxRegistMail(mailDTO); // 메일을 개별적으로 저장
        }
    }

    @Transactional
    public void sentRegistMail(MailDTO mailDTO, List<String> recipientId, List<String> recipientName) {
        for (int i = 0; i < recipientId.size(); i++) {
            mailDTO.setRecipientId(recipientId.get(i));  // 수신자 ID
            mailDTO.setRecipientName(recipientName.get(i));  // 수신자 이름
            mailMapper.sentRegistMail(mailDTO); // 메일을 개별적으로 저장
        }
    }

    @Transactional
    public void inboxDeleteMails(List<Integer> mailIds, String userCode) {

        if (mailIds == null || mailIds.isEmpty()) {
            throw new IllegalArgumentException("메일 ID 목록이 비어 있습니다.");
        }

        if (userCode == null || userCode.isEmpty()) {
            throw new IllegalArgumentException("사용자 코드가 유효하지 않습니다.");
        }

        mailMapper.inboxDeleteMails(mailIds, userCode);

    }

    @Transactional
    public void sentDeleteMails(List<Integer> mailIds, String userCode) {

        if (mailIds == null || mailIds.isEmpty()) {
            throw new IllegalArgumentException("메일 ID 목록이 비어 있습니다.");
        }

        if (userCode == null || userCode.isEmpty()) {
            throw new IllegalArgumentException("사용자 코드가 유효하지 않습니다.");
        }

        mailMapper.sentDeleteMails(mailIds, userCode);
    }
}




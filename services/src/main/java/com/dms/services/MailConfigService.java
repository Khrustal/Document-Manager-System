package com.dms.services;

import com.dms.dao.MailConfigRepository;
import com.dms.model.MailConfig;
import org.springframework.stereotype.Service;

@Service
public class MailConfigService {

    MailConfigRepository repo;

    public MailConfigService(MailConfigRepository repo) {
        this.repo = repo;
    }

    public boolean getMailStatus() {
        return repo.findById(1L).orElseThrow(RuntimeException::new).getStatus();
    }

    public void enableMail() {
        MailConfig mailConfig = repo.findById(1L).orElseThrow(RuntimeException::new);
        mailConfig.setStatus(true);
        repo.save(mailConfig);
    }

    public void disableMail() {
        MailConfig mailConfig = repo.findById(1L).orElseThrow(RuntimeException::new);
        mailConfig.setStatus(false);
        repo.save(mailConfig);
    }
}
